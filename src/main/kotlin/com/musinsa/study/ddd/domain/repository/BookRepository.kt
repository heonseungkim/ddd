package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Book
import com.musinsa.study.ddd.domain.value.BookId
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, BookId>
