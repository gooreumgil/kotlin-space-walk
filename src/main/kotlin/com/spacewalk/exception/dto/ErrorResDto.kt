package com.spacewalk.exception.dto

class ErrorResDto(
    val message: String? = null,
    val code: String? = null,
    val errors: Map<String, String>? = null
)