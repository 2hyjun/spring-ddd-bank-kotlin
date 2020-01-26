package org.zosh.springdddbankkotlin.bankclient.ui.dto

data class CreateAccountDto(
    val username: String,
    val accountName: String?
)
