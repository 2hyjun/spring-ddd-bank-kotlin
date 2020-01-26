package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import org.springframework.stereotype.Repository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankAccountRepository

@Repository
class BankAccountRepositoryImpl(
    private val crudRepository: BankAccountCrudRepository
) : BankAccountRepository {
    override fun save(entity: BankAccount) = crudRepository.save(entity)
}
