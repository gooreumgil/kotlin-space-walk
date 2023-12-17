package com.spacewalk.security.dto

import javax.validation.constraints.NotBlank

data class LoginReqDto(

    @field:NotBlank(message = "아이디는 필수 항목입니다.")
    val username: String,

    @field:NotBlank(message = "비밀번호는 필수 항목입니다.")
    val password: String

)
