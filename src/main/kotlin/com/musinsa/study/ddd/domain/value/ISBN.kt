package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ISBN(
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    val value: String,
) : Serializable
