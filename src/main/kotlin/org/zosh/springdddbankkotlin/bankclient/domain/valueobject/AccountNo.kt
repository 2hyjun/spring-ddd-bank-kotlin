package org.zosh.springdddbankkotlin.bankclient.domain.valueobject

/**
 * Typed Value Object holding an {@link BankAccount} number used to uniquely
 * identify an BankAccount.
 */
data class AccountNo(
    val number: Long
) {
    constructor(numberStr: String) : this(numberStr.toLong())

    override fun hashCode() = 31 + number.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is AccountNo) {
            return false
        }

        return this.number == other.number
    }

    fun toLong() = this.number
}
