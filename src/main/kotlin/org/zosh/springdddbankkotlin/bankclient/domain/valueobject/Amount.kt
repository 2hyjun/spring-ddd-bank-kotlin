package org.zosh.springdddbankkotlin.bankclient.domain.valueobject

import javax.persistence.Embeddable
import kotlin.math.roundToLong

@Embeddable
data class Amount(
    var cents: Long
) {
    companion object {
        @JvmStatic
        val ZERO = Amount(0, 0)
    }

    constructor(euros: Int, cents: Int) : this(cents = (100.0 * euros + cents).roundToLong())
    constructor(euros: Double) : this(cents = (100.0 * euros).roundToLong())

    fun plus(rhs: Amount) = Amount(euros = this.toDouble() + rhs.toDouble())
    fun minus(rhs: Amount) = Amount(euros = this.toDouble() - rhs.toDouble())
    fun times(factor: Double) = Amount(euros = this.toDouble() * factor)

    fun toDouble(): Double {
        return cents / 100.0
    }

    operator fun compareTo(other: Amount) = this.toDouble().compareTo(other.toDouble())

    override fun toString() = this.cents.toString()
}
