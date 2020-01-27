package org.zosh.springdddbankkotlin.bankclient.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.NamedEntityGraphs

@NamedEntityGraphs(
    value = [
        NamedEntityGraph(
            name = "accountAccess.account",
            attributeNodes = [NamedAttributeNode(value = "bankAccount")]
        )
    ]
)
@Entity
class AccountAccess(
    @Column(columnDefinition = "boolean default false")
    val owner: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_client_id")
    val bankClient: BankClient,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    var bankAccount: BankAccount
) : EntityBase<AccountAccess>() {
    constructor() : this(owner = false, bankClient = BankClient(), bankAccount = BankAccount())
}
