package com.musinsa.study.ddd.controller.dto

data class ErrorResponse(
    val errorCode: Int,
    val message: String?,
)
