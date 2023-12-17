package com.spacewalk.security

data class UserContext(
    val id: Long,
    val username: String,
    val age: Int,
    val roles: Set<Any?>
)