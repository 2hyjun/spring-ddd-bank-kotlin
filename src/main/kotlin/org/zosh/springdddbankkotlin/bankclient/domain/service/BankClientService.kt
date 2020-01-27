package org.zosh.springdddbankkotlin.bankclient.domain.service

import java.time.Instant
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient
import org.zosh.springdddbankkotlin.bankclient.domain.exception.AccountNotFoundException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.CannotDeleteClientException
import org.zosh.springdddbankkotlin.bankclient.domain.repository.AccountAccessRepository
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankAccountRepository
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankClientRepository
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.Amount

/**
 * TODO
 *  1. application service, domain service 구분
 *  2. domain logic에 영향을 주지 않는 side effect 들 application service 로 분리
 */
@Service
class BankClientService(
    private val bankClientRepository: BankClientRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val accountAccessRepository: AccountAccessRepository
) {
    fun getAllClient(): List<BankClient> {
        return bankClientRepository.findAll()
    }

    fun createClient(username: String, birthDate: Instant): BankClient {
        val bankClient = BankClient(username, birthDate).validate()
        return bankClientRepository.save(bankClient)
    }

    @Transactional
    fun deleteClient(client: BankClient) {
        if (!client.possibleToDelete()) {
            throw CannotDeleteClientException("client#${client.id} has accounts as owner")
        }

        accountAccessRepository.deleteAll(client.accountAccesses)
        bankClientRepository.delete(client)
    }

    /**
     * 원본 예제의 구현
     * Client Entity 가 accountRepository, accountAccessRepository 를 가지고 있고,
     * domain method (createAccount)에서 db에 저장된 값을 return 해줌.
     *
     * public AccountAccess createAccount(final String accountName) {
     *  final Account account = accountRepository.save(new Account(accountName));
     *  final AccountAccess accountAccess = new AccountAccess(this, true, account);
     *  return accountAccessRepository.save(accountAccess);
     * }
     *
     */
    @Transactional
    fun createAccount(client: BankClient, accountName: String?): AccountAccess {
        val (account, access) = client.createAccount(accountName ?: client.nextDefaultAccountName())
        val savedAccount = bankAccountRepository.save(account)
        val accessWithAccount = access.apply { bankAccount = savedAccount }
        return accountAccessRepository.save(accessWithAccount)
    }

    fun addManagerToAccount(manager: BankClient, account: BankAccount): AccountAccess {
        val accountAccess = AccountAccess(owner = false, bankClient = manager, bankAccount = account)
        return accountAccessRepository.save(accountAccess)
    }

    fun deposit(client: BankClient, destination: AccountNo, amount: Amount): BankAccount {
        val newAccount = client.deposit(destination, amount)
        return bankAccountRepository.save(newAccount)
    }

    @Transactional
    fun transfer(client: BankClient, sourceAccountNo: AccountNo, destinationAccountNo: AccountNo, amount: Amount): BankAccount {
        val destination = bankAccountRepository.findByAccountNo(destinationAccountNo)
            .orElseThrow { AccountNotFoundException(destinationAccountNo, null) }

        val (newSource, newDestination) = client.transfer(sourceAccountNo, destination, amount)
        bankAccountRepository.save(newSource)
        bankAccountRepository.save(newDestination)
        return newDestination
    }
}
