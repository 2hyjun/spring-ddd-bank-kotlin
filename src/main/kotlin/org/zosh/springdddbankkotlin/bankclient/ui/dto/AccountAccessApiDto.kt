package org.zosh.springdddbankkotlin.bankclient.ui.dto

import java.time.Instant
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient

data class AccountAccessApiDto(
    val owner: Boolean,
    val bankClient: BankClientInnerDto,
    var bankAccount: BankAccount
) {
    data class BankClientInnerDto(
        val username: String,
        val birthDate: Instant
    ) {
        constructor(entity: BankClient) : this(
            username = entity.username,
            birthDate = entity.birthDate
        )
    }

    constructor(entity: AccountAccess) : this(
        owner = entity.owner,
        bankAccount = entity.bankAccount,
        bankClient = BankClientInnerDto(entity.bankClient)
    )
}

fun AccountAccess.toApiDto() = AccountAccessApiDto(this)
