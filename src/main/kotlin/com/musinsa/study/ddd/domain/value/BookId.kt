package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class BookId(
    @Column(name = "book_id", nullable = false)
    val value: Long,
) : Serializable
