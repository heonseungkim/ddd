package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.BookId
import com.musinsa.study.ddd.domain.value.BookStatus
import com.musinsa.study.ddd.domain.value.ISBN
import com.musinsa.study.ddd.domain.value.ReservationStatus
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
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

    fun changeStatusOnLoan() {
        this.status = BookStatus.ON_LOAN
    }
    fun changeStatusAvailable() {
        this.status = BookStatus.AVAILABLE
    }

}
