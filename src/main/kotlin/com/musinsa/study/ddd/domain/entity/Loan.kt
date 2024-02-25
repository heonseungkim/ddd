package com.musinsa.study.ddd.domain.entity

import com.musinsa.study.ddd.domain.value.LoanId
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
data class Loan(
    @EmbeddedId
    val id: LoanId,
    @ManyToOne
    @JoinColumn(name = "book_id")
    val book: Book,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    @Column(name = "loaned")
    val loaned: LocalDateTime,
    @Column(name = "returned", nullable = true)
    @ColumnDefault("NULL")
    var returned: LocalDateTime?,
) {
    constructor(member: Member, book: Book) : this(
        id = LoanId(System.currentTimeMillis()),
        book = book,
        member = member,
        loaned = LocalDateTime.now(),
        returned = null,
    ) {
        if (!member.isLoanPossible()) {
            throw IllegalStateException("${member.name}님은 이미 대여중인 도서가 있습니다.")
        }
        if (book.isOnLoan()) {
            throw IllegalStateException("이미 대여중인 책입니다.")
        }

        book.changeStatusOnLoan()
        member.addLoan(this)
    }

    fun `return`() {
        if (this.returned != null) {
            throw IllegalStateException("이미 반납된 대여건입니다.")
        }
        this.book.changeStatusAvailable()
        this.returned = LocalDateTime.now()
    }
}
