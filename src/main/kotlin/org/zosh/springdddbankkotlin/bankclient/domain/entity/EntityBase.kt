package org.zosh.springdddbankkotlin.bankclient.domain.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityBase<T : EntityBase<T>> {
    companion object {
        const val DEFAULT_ID = -1L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = DEFAULT_ID

    override fun hashCode() = if (this.persisted) this.id.hashCode() else 0

    override fun equals(other: Any?): Boolean {
        if (other !is EntityBase<*>) {
            return false
        }

        this.checkPersisted()
        other.checkPersisted()

        return this.id == other.id
    }

    private fun checkPersisted() {
        if (!this.persisted) throw IllegalStateException("Identity missing in identity $this")
    }

    private val persisted: Boolean
        get() = this.id != DEFAULT_ID
}
