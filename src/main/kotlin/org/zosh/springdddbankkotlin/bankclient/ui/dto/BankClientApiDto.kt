package org.zosh.springdddbankkotlin.bankclient.ui.dto

import java.time.Instant
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient

data class BankClientApiDto(
    val username: String,
    val birthDate: Instant,
    val accountAccesses: List<AccountAccessInnerDto>
) {
    data class AccountAccessInnerDto(
        val owner: Boolean,
        val bankAccount: BankAccount
    ) {
        constructor(access: AccountAccess) : this(
            owner = access.owner,
            bankAccount = access.bankAccount
        )
    }

    constructor(bankClient: BankClient) : this(
        username = bankClient.username,
        birthDate = bankClient.birthDate,
        accountAccesses = bankClient.accountAccesses.map { AccountAccessInnerDto(it) }
    )
}

fun BankClient.toApiDto() = BankClientApiDto(this)
