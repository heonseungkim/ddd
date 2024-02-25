package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.ReservationId
import com.musinsa.study.ddd.domain.value.ReservationStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Reservation(
    @EmbeddedId
    val id: ReservationId,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne
    @JoinColumn(name = "book_id") // 이 테이블의 참조키 컬럼명
    val book: Book,

    @Column(name = "reserved", nullable = false)
    val reserved: LocalDateTime,

    @Enumerated(value = EnumType.STRING)
    var status: ReservationStatus,
) {
    constructor(member: Member, book: Book) : this(
        id = ReservationId(value = System.currentTimeMillis()),
        member = member,
        book = book,
        reserved = LocalDateTime.now(),
        status = ReservationStatus.ON_RESERVATION,
    ) {
        if (book.isAvailable()) {
            throw IllegalStateException("대여 가능한 상태이므로 예약이 불가한 도서입니다.")
        }
        book.reservations.add(this)
    }

    fun cancel() {
        this.status = ReservationStatus.CANCELED
    }

}
