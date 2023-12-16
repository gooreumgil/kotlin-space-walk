package com.spacewalk.domain.user.dto

import com.spacewalk.domain.user.User
import java.time.LocalDateTime

data class UserResDto (

    val id: Long,
    val username: String,
    val age: Int,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime

) {
    companion object {
        fun fromUser(user: User): UserResDto {
            return UserResDto(user.id!!, user.username, user.age, user.createdAt!!, user.lastModifiedAt!!)
        }
    }

}