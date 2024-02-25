package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Loan
import com.musinsa.study.ddd.domain.value.LoanId
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loan, LoanId>
