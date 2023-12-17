package com.spacewalk.domain.article.dto

import javax.validation.constraints.NotBlank

data class ArticleUpdateReqDto (

    @field:NotBlank(message = "제목은 필수 항목입니다.")
    val title: String,

    @field:NotBlank(message = "내용은 필수 항목입니다.")
    val content: String
) {}