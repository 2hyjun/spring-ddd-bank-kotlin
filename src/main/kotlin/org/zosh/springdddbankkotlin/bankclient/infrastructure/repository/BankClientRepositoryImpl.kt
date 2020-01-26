package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import java.util.Optional
import org.springframework.stereotype.Repository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankClientRepository

@Repository
class BankClientRepositoryImpl(
    private val crudRepository: BankClientCrudRepository
) : BankClientRepository {
    override fun save(entity: BankClient) = crudRepository.save(entity)
    override fun delete(entity: BankClient) = crudRepository.delete(entity)
    override fun findById(id: Long) = crudRepository.findById(id)

    override fun findAll() = crudRepository.findAllWithAccountAccess()
    override fun findByUsername(username: String): Optional<BankClient> = crudRepository.findByUsername(username)
}
