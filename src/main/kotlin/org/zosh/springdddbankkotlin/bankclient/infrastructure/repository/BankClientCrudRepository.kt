package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import java.util.Optional
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient

interface BankClientCrudRepository : JpaRepository<BankClient, Long> {
    fun findByUsername(username: String): Optional<BankClient>

    @EntityGraph(value = "bankClient.accountAccesses")
    @Query("SELECT c FROM BankClient c")
    fun findAllWithAccountAccess(): List<BankClient>

    @EntityGraph(value = "bankClient.accountAccesses", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM BankClient c WHERE c.username = :username")
    fun findByUsernameWithAccountAccess(@Param("username") username: String): Optional<BankClient>

    @EntityGraph(value = "bankClient.accountAccesses", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM BankClient c WHERE c.id = :id")
    fun findByIdWithAccountAccess(@Param("id") id: Long): Optional<BankClient>
}
