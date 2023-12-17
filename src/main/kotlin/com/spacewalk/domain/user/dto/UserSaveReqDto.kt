package com.spacewalk.domain.user.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserSaveReqDto (

    @field:NotBlank(message = "이름은 필수 항목입니다.")
    val username: String,

    @field:NotBlank(message = "이메일은 필수 항목입니다.")
    @field:Email(message = "유효한 이메일 주소를 입력해주세요.")
    var email: String,

    @field:NotBlank(message = "비밀번호는 필수 항목입니다.")
    @field:Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    var password: String,

    val age: Int,
)