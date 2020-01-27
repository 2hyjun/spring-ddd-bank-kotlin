package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import java.util.Optional
import org.springframework.stereotype.Repository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankAccountRepository
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo

@Repository
class BankAccountRepositoryImpl(
    private val crudRepository: BankAccountCrudRepository
) : BankAccountRepository {
    override fun findByAccountNo(accountNo: AccountNo): Optional<BankAccount> = crudRepository.findById(accountNo.number)

    override fun save(entity: BankAccount) = crudRepository.save(entity)
}
