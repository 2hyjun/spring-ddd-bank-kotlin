package org.zosh.springdddbankkotlin.bankclient.ui.dto

data class TransferDto(
    val clientName: String,
    val sourceAccountNo: Long,
    val destinationAccountNo: Long,
    val amount: Double
)
