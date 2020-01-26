package org.zosh.springdddbankkotlin.bankclient.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess

interface AccountAccessCrudRepository : JpaRepository<AccountAccess, Long>
