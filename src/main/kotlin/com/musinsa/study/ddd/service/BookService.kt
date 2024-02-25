package com.musinsa.study.ddd.service

import com.musinsa.study.ddd.domain.entity.Book
import com.musinsa.study.ddd.domain.repository.BookRepository
import com.musinsa.study.ddd.domain.value.BookId
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository,
) {
    fun get(
        bookId: BookId
    ): Book = bookRepository.findById(bookId).orElseThrow { NoSuchElementException("${bookId.value} 책이 존재하지 않습니다.") }
}
