package com.musinsa.study.ddd.controller.dto

data class CreateLoanRequest(
    val memberId: Long,
    val bookId: Long,
)
