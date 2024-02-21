package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.*
import jakarta.persistence.*
import org.springframework.boot.context.properties.bind.DefaultValue
import java.time.LocalDateTime

@Entity
data class Book(
    @EmbeddedId
    val id: BookId,

    @Column(name = "title", length = 150, nullable = false)
    val title: String,

    @Column(name = "author", length = 50, nullable = false)
    val author: String,

    @Embedded
    val isbn: ISBN,

    @Column(name = "publisher", length = 50, nullable = false)
    val publisher: String,

    @Column(name = "published")
    @DefaultValue("NULL")
    val published: LocalDateTime,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    var status: BookStatus,

    @OneToMany(mappedBy = "book") // 주인의 필드
    val reservations: MutableList<Reservation>,
) {
    fun isReservationAvailable(): Boolean =
        this.status == BookStatus.ON_LOAN && this.reservations.none {
            it.status == ReservationStatus.ON_RESERVATION
        }

    fun isOnLoan(): Boolean = this.status == BookStatus.ON_LOAN
    fun isAvailable(): Boolean = this.status == BookStatus.AVAILABLE
    fun loan(member: Member) {
        if (isOnLoan()) {
            throw IllegalStateException("이미 대여된 도서입니다.")
        }

        this.status = BookStatus.ON_LOAN
        this.reservations.first {
            it.book == this && it.member == member
        }.status = ReservationStatus.ON_LOAN
    }

    fun reserve(member: Member) {
        if (isAvailable()) {
            throw IllegalStateException("대여 가능한 상태이므로 예약이 불가한 도서입니다.")
        }

        val reservation = Reservation(
            member = member,
            book = this,
        )

        this.status = BookStatus.ON_LOAN
    }
}
