package org.zosh.springdddbankkotlin.bankclient.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.Amount

/**
 * An account, which a client of a bank can manage. This entity is an Anemic
 * Domain Object, as it only has stupid setters.
 */
@Entity
class BankAccount(
    @Column val name: String,
    var balance: Amount = Amount.ZERO
) : EntityBase<BankAccount>() {

    companion object {
        val MINIMUM_BALANCE = Amount(-1000, 0)
    }

    constructor() : this(name = "")

    @JsonIgnore
    fun getAccountNo(): AccountNo = AccountNo(this.id)

    override fun toString(): String {
        return String.format("BankAccount{accountNo=%d, name='%s', balance='%s'}", getAccountNo(), name, balance)
    }
}
