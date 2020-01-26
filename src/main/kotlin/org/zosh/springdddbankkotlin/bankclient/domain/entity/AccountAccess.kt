package org.zosh.springdddbankkotlin.bankclient.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class AccountAccess(
    @Column(columnDefinition = "boolean default false")
    val owner: Boolean,
    @ManyToOne
    val bankClient: BankClient,
    @ManyToOne
    var bankAccount: BankAccount
) : EntityBase<AccountAccess>() {
    constructor() : this(owner = false, bankClient = BankClient(), bankAccount = BankAccount())
}
