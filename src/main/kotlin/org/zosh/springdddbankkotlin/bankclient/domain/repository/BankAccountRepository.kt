package org.zosh.springdddbankkotlin.bankclient.domain.repository

import java.util.Optional
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankAccount
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo

interface BankAccountRepository : DefaultRepository<BankAccount> {
    fun findByAccountNo(accountNo: AccountNo): Optional<BankAccount>
}
