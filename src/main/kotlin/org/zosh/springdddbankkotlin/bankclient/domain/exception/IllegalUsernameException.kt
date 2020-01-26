package org.zosh.springdddbankkotlin.bankclient.domain.exception

class IllegalUsernameException(username: String) : BaseException("Illegal Username: $username")
