package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class MemberId(
    @Column(name = "member_id", nullable = false, unique = true)
    val value: Long,
) : Serializable
