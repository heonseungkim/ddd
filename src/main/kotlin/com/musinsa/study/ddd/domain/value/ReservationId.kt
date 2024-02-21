package com.musinsa.study.ddd.domain.value

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class ReservationId(
    @Column(name = "reservation_id", nullable = false, unique = true)
    val value: Long,
) : Serializable
