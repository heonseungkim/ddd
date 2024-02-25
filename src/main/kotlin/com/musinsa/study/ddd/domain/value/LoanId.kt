package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class LoanId(
    @Column(name = "loan_id", nullable = false, unique = true)
    val value: Long,
) : Serializable
