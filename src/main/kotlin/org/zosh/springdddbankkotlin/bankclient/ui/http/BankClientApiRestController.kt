package org.zosh.springdddbankkotlin.bankclient.ui.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.zosh.springdddbankkotlin.bankclient.domain.entity.AccountAccess
import org.zosh.springdddbankkotlin.bankclient.domain.entity.BankClient
import org.zosh.springdddbankkotlin.bankclient.domain.exception.AccountNotFoundException
import org.zosh.springdddbankkotlin.bankclient.domain.exception.BankClientNotFoundException
import org.zosh.springdddbankkotlin.bankclient.domain.repository.BankClientRepository
import org.zosh.springdddbankkotlin.bankclient.domain.service.BankClientService
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.AccountNo
import org.zosh.springdddbankkotlin.bankclient.domain.valueobject.Amount
import org.zosh.springdddbankkotlin.bankclient.ui.dto.AddAccountManagerDto
import org.zosh.springdddbankkotlin.bankclient.ui.dto.CreateAccountDto
import org.zosh.springdddbankkotlin.bankclient.ui.dto.CreateClientDto
import org.zosh.springdddbankkotlin.bankclient.ui.dto.DepositDto

@RestController
class BankClientApiRestController(
    private val bankClientService: BankClientService,
    private val bankClientRepository: BankClientRepository
) {
    @GetMapping("/bank")
    fun index(): List<BankClient> = bankClientService.getAllClient()

    @PostMapping("/bank/client")
    fun createClient(
        @RequestBody body: CreateClientDto
    ) = bankClientService.createClient(body.username, body.birthDate)

    @PostMapping("/bank/client/account")
    fun createAccount(
        @RequestBody body: CreateAccountDto
    ): AccountAccess {
        val client = bankClientRepository.findByUsername(body.username)
            .orElseThrow { BankClientNotFoundException("username ${body.username} not_found") }
        return bankClientService.createAccount(client, body.accountName)
    }

    @PostMapping("/bank/client/{id}")
    fun deleteClient(
        @PathVariable id: Long
    ) {
        val client = bankClientRepository.findById(id)
            .orElseThrow { BankClientNotFoundException("client#$id not_found") }
        return bankClientService.deleteClient(client)
    }

    @PostMapping("/bank/client/account:addManager")
    fun addAccountManager(
        @RequestBody body: AddAccountManagerDto
    ): AccountAccess {
        val client = bankClientRepository.findByUsername(body.username)
            .orElseThrow { BankClientNotFoundException("username ${body.username} not_found") }

        val accountNo = AccountNo(body.accountNo)
        val account = client.findAccount(accountNo)
            .orElseThrow { AccountNotFoundException(accountNo, client) }

        val manager = bankClientRepository.findByUsername(body.managerName)
            .orElseThrow { BankClientNotFoundException("username ${body.username} not_found") }

        return bankClientService.addManagerToAccount(manager, account)
    }

    @PostMapping("/bank/client:deposit")
    fun deposit(
        @RequestBody body: DepositDto
    ) {
        val client = bankClientRepository.findByUsername(body.clientName)
            .orElseThrow { BankClientNotFoundException("clientName ${body.clientName} not_found") }

        bankClientService.deposit(
            client = client,
            destination = AccountNo(body.accountNo),
            amount = Amount(body.amount)
        )
    }
}
