package org.zosh.springdddbankkotlin.bankclient.domain.repository

import java.util.Optional
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient

interface BankClientRepository : DefaultRepository<BankClient> {
    fun findById(id: Long): Optional<BankClient>
    fun findAll(): List<BankClient>
    fun findByUsername(username: String): Optional<BankClient>
    fun delete(entity: BankClient)
}
