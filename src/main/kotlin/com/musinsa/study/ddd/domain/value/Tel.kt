package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Tel(
    @Column(name = "tel", length = 13, nullable = false, unique = true)
    val value: String,
) : Serializable
