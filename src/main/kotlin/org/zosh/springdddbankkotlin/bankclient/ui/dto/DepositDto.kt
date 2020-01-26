package org.zosh.springdddbankkotlin.bankclient.ui.dto

data class DepositDto(
    val clientName: String,
    val accountNo: Long,
    val amount: Double
)
