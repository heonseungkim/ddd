package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.MemberId
import com.musinsa.study.ddd.domain.value.ReservationStatus
import com.musinsa.study.ddd.domain.value.Tel
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

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

    @OneToMany(mappedBy = "member")
    var loans: MutableList<Loan>,

) {
    fun checkReservations(
        status: ReservationStatus
    ): List<Reservation> = reservations.filter { it.status == status }.toList()

    fun isLoanPossible() = this.loans.isEmpty() ||
            this.loans.none { it.returned == null }

    fun addLoan(loan: Loan) {
        this.loans.add(loan)
    }

}
