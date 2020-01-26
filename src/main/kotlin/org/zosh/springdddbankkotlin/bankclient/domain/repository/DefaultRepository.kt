package org.zosh.springdddbankkotlin.bankclient.domain.repository

interface DefaultRepository<T> {
    fun save(entity: T): T
}
