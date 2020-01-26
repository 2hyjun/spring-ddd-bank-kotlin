package org.zosh.springdddbankkotlin.bankclient.domain.exception

import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo

class AccountNotFoundException(accountNo: AccountNo, client: BankClient?) :
    BaseException("account#${accountNo.toLong()} ${client?.let { "of client ${it.username}" } ?: ""} not found")
