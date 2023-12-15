package com.spacewalk.domain.account.dto

import com.spacewalk.domain.account.Account

data class AccountResDto (

    val id: Long,
    val username: String,
    val age: Int

) {
    companion object {
        fun fromAccount(account: Account): AccountResDto {
            return AccountResDto(account.id!!, account.username, account.age)
        }
    }

}