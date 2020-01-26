package org.zosh.springdddbankkotlin.bankclient.domain.repository

import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess

interface AccountAccessRepository : DefaultRepository<AccountAccess> {
    fun deleteAll(entities: Set<AccountAccess>)
}
