package org.zosh.springdddbankkotlin.bankclient.domain.entity

import java.time.Instant
import java.util.Optional
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.NamedSubgraph
import javax.persistence.OneToMany
import org.zosh.springdddbankkotlin.bankclient.domain.exception.AccountNotFoundException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.IllegalUsernameException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.InsufficientBalanceException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.InvalidDepositAmountException
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.Amount

/**
 * A client of a bank along with some methods he can do.
 */
@NamedEntityGraph(
    name = "bankClient.accountAccesses",
    attributeNodes = [NamedAttributeNode(value = "accountAccesses", subgraph = "sub.accountAccess.account")],
    subgraphs = [
        NamedSubgraph(
            name = "sub.accountAccess.account",
            attributeNodes = [NamedAttributeNode(value = "bankAccount")]
        )
    ]

)
@Entity
class BankClient(
    @Column(unique = true, nullable = false) val username: String,
    @Column val birthDate: Instant
) : EntityBase<BankClient>() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankClient")
    val accountAccesses: Set<AccountAccess> = emptySet()

    companion object {
        val USER_NAME_REGEX = Regex("[a-z_A-Z][a-z_A-Z0-9]{0,30}")
    }

    /** Necessary for JPA entities internally. */
    constructor() : this("", Instant.EPOCH)

    override fun toString(): String {
        return String.format("BankClient{id=%d, name='%s', birthDate='%s'}", this.id, username, birthDate)
    }

    /**
     * client 가 가진 account 중 owner 인 것이 하나도 없어야함.
     */
    fun possibleToDelete() = !this.accountAccesses.any { it.owner }

    fun nextDefaultAccountName() = "${this.username}-account#${this.accountAccesses.size + 1}"

    fun validate() =
        if (!USER_NAME_REGEX.matches(this.username)) throw IllegalUsernameException(this.username)
        else this

    fun deposit(accountNo: AccountNo, amount: Amount): BankAccount {
        if (amount <= Amount.ZERO) {
            throw InvalidDepositAmountException("Amount should be over ZERO")
        }

        val account = this.findMyAccount(accountNo)
            .orElseThrow { AccountNotFoundException(accountNo, this) }

        return account.apply { balance = balance.plus(amount) }
    }

    fun createAccount(accountName: String): Pair<BankAccount, AccountAccess> {
        val account = BankAccount(name = accountName)
        return Pair(account, AccountAccess(owner = true, bankClient = this, bankAccount = account))
    }

    fun findMyAccount(accountNo: AccountNo) =
        Optional.ofNullable(this.accountAccesses.find { it.bankAccount.getAccountNo() == accountNo }?.bankAccount)

    fun accountsReport(): String {
        val report = StringBuilder().append(String.format("Accounts of client: %s\n", username))
        this.accountAccesses.forEach {
            val accessRight = if (it.owner) "isOwner" else "manages"
            val account = it.bankAccount
            report.append(
                String.format(
                    "%s\t%s\t%5.2f\t%s\n", account.getAccountNo(), accessRight,
                    account.balance.toDouble(), account.name
                )
            )
        }

        return report.toString()
    }

    fun transfer(sourceAccountNo: AccountNo, destination: BankAccount, amount: Amount): Pair<BankAccount, BankAccount> {
        val source = this.findMyAccount(sourceAccountNo)
            .orElseThrow { AccountNotFoundException(sourceAccountNo, this) }

        if (source.balance < amount) {
            throw InsufficientBalanceException("Transfer requested amount: ${amount.toDouble()}, current balance: ${source.balance.toDouble()}")
        }

        source.apply { balance = balance.minus(amount) }
        destination.apply { balance = balance.plus(amount) }

        return Pair(source, destination)
    }
}
