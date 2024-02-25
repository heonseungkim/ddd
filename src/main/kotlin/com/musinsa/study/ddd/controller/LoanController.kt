package com.musinsa.study.ddd.controller

import com.musinsa.study.ddd.controller.dto.CreateLoanRequest
import com.musinsa.study.ddd.controller.dto.ReturnLoanRequest
import com.musinsa.study.ddd.domain.value.BookId
import com.musinsa.study.ddd.domain.value.LoanId
import com.musinsa.study.ddd.domain.value.MemberId
import com.musinsa.study.ddd.service.LoanService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loan")
@Tag(name = "loan")
class LoanController(
    val loanService: LoanService,
) {

    @Operation(summary = "대여하기")
    @PutMapping
    fun createLoan(
        @RequestBody createLoanRequest: CreateLoanRequest
    ): String {
        loanService.create(
            memberId = MemberId(createLoanRequest.memberId),
            bookId = BookId(createLoanRequest.bookId),
        )

        return "성공"
    }

    @Operation(summary = "반납하기")
    @PostMapping("/return")
    fun returnLoan(
        @RequestBody returnLoanRequest: ReturnLoanRequest,
    ): String {
        loanService.`return`(
            loanId = LoanId(returnLoanRequest.loanId),
        )

        return "성공"
    }
}
