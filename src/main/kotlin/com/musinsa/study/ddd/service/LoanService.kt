package com.musinsa.study.ddd.service

import com.musinsa.study.ddd.domain.entity.Loan
import com.musinsa.study.ddd.domain.repository.LoanRepository
import com.musinsa.study.ddd.domain.value.BookId
import com.musinsa.study.ddd.domain.value.LoanId
import com.musinsa.study.ddd.domain.value.MemberId
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LoanService(
    val loanRepository: LoanRepository,
    val bookService: BookService,
    val memberService: MemberService,
) {

    @Transactional
    fun create(
        memberId: MemberId,
        bookId: BookId,
    ) {
        val member = memberService.get(memberId)
        val book = bookService.get(bookId)
        val loan = Loan(
            member = member,
            book = book,
        )
        loanRepository.save(loan)
    }

    @Transactional
    fun `return`(
        loanId: LoanId,
    ) {
        val loan = loanRepository.findById(loanId).orElseThrow { NoSuchElementException("${loanId.value} 대여 정보가 없습니다.") }
        loan.`return`()
    }
}
