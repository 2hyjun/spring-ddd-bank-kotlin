package org.zosh.springdddbankkotlin.bankclient.ui.dto

import java.time.Instant

data class CreateClientDto(
    val username: String,
    val birthDate: Instant = Instant.now()
)
