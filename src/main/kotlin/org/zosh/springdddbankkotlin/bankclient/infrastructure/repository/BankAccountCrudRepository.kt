package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount

interface BankAccountCrudRepository : JpaRepository<BankAccount, Long>
