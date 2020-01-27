package org.zosh.springdddbankkotlin.bankclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDddBankKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringDddBankKotlinApplication>(*args)
}
