package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient

interface BankClientCrudRepository : JpaRepository<BankClient, Long> {
    fun findByUsername(username: String): Optional<BankClient>
    @Query("SELECT c FROM BankClient c")
    fun findAllWithAccountAccess(): List<BankClient>
}
