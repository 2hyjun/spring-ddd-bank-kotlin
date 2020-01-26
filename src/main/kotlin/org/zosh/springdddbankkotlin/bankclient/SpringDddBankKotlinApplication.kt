package org.zosh.springdddbankkotlin.bankclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * TODO
 *  1. 기본 기능 구현
 *  2. client 에 email field 추가
 *  3. client 생성, account 생성, deposit, transfer 시 email 보내기
 *  4. deposit, transfer history 만들기.
 */

@SpringBootApplication
class SpringDddBankKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringDddBankKotlinApplication>(*args)
}
