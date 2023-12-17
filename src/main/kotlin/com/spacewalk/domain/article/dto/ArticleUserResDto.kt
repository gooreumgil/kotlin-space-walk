package com.spacewalk.domain.article.dto

import com.spacewalk.domain.user.User
import com.spacewalk.utils.AES256Util


data class ArticleUserResDto (
    val id: Long,
    val username: String,
    val email: String
) {
    companion object {
        fun fromUser(user: User): ArticleUserResDto {
            return ArticleUserResDto(id = user.id!!, username = user.username, email =  AES256Util.decrypt(user.email))
        }
    }
}