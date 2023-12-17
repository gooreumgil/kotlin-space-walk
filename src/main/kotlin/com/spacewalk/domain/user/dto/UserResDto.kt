package com.spacewalk.domain.user.dto

import com.spacewalk.domain.user.User
import com.spacewalk.utils.AES256Util
import java.time.LocalDateTime

data class UserResDto (

    val id: Long,
    val username: String,
    val email: String? = null,
    val age: Int,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime

) {
    companion object {
        fun fromUser(user: User): UserResDto {
            return UserResDto(user.id!!, user.username, AES256Util.decrypt(user.email), user.age, user.createdAt!!, user.lastModifiedAt!!)
        }
    }

}