package org.zosh.springdddbankkotlin.bankclient.domain.entity

import java.time.Instant
import java.util.Optional
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.OneToMany
import org.zosh.springdddbankkotlin.bankclient.domain.exception.AccountNotFoundException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.IllegalUsernameException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.InvalidDepositAmountException
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.Amount

/**
 * A client of a bank along with some methods he can do.
 */
@NamedEntityGraph(
    name = "bankClient.accountAccesses",
    attributeNodes = [NamedAttributeNode(value = "accountAccesses")]
)
@Entity
class BankClient(
    @Column(unique = true, nullable = false) val username: String,
    @Column val birthDate: Instant
) : EntityBase<BankClient>() {

    @OneToMany(fetch = FetchType.LAZY)
    val accountAccesses: Set<AccountAccess> = emptySet()

    companion object {
        val USER_NAME_REGEX = Regex("[a-z_A-Z][a-z_A-Z0-9]{0,30}")
    }

    /** Necessary for JPA entities internally. */
    constructor() : this("", Instant.EPOCH)

    override fun toString(): String {
        return String.format("BankClient{id=%d, name='%s', birthDate='%s'}", this.id, username, birthDate)
    }

    fun createAccount(accountName: String): Pair<BankAccount, AccountAccess> {
        val account = BankAccount(name = accountName)
        return Pair(account, AccountAccess(owner = true, bankClient = this, bankAccount = account))
    }

    /**
     * client 가 가진 account 중 owner 인 것이 하나도 없어야함.
     */
    val possibleToDelete
        get() = !this.accountAccesses.any { it.owner }

    val nextDefaultAccountName
        get() = "${this.username}-account#${this.accountAccesses.size + 1}"

    fun validate() =
        if (!USER_NAME_REGEX.matches(this.username)) throw IllegalUsernameException(this.username)
        else this

    fun deposit(accountNo: AccountNo, amount: Amount): BankAccount {
        if (amount.compareTo(Amount.ZERO) <= 0) {
            throw InvalidDepositAmountException("Amount should be over ZERO")
        }

        val account = this.findAccount(accountNo)
            .orElseThrow { AccountNotFoundException(accountNo, this) }

        return account.apply { balance = balance.plus(amount) }
    }

    fun findAccount(accountNo: AccountNo) =
        Optional.ofNullable(this.accountAccesses.find { it.bankAccount.accountNo == accountNo }?.bankAccount)
}
