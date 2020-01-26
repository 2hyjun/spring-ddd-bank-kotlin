package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import org.springframework.stereotype.Repository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess
import org.zosh.springdddbankkotlin.bankclient.domain.repository.AccountAccessRepository

@Repository
class AccountAccessRepositoryImpl(
    private val crudRepository: AccountAccessCrudRepository
) : AccountAccessRepository {
    override fun deleteAll(entities: Set<AccountAccess>) = crudRepository.deleteAll(entities)
    override fun save(entity: AccountAccess) = crudRepository.save(entity)
}
