package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.MemberId
import com.musinsa.study.ddd.domain.value.ReservationStatus
import com.musinsa.study.ddd.domain.value.Tel
import jakarta.persistence.*

@Entity
data class Member(
    @EmbeddedId
    val id: MemberId,

    @Column(length = 30, nullable = false)
    val name: String,

    @Embedded
    val tel: Tel,

    @OneToMany(mappedBy = "member")
    val reservations: List<Reservation>,
) {
    fun checkReservations(
        status: ReservationStatus
    ): List<Reservation> = reservations.filter { it.status == status }.toList()

}
