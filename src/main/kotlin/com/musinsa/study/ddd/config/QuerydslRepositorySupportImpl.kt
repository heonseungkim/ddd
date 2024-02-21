package com.musinsa.study.ddd.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class QuerydslRepositorySupportImpl(
    domain: Class<*>,
) : QuerydslRepositorySupport(
    domain
) {
    override fun getQuerydsl(): Querydsl {
        return super.getQuerydsl()!!
    }

    override fun getEntityManager(): EntityManager {
        return super.getEntityManager()!!
    }

    @PersistenceContext(unitName = "DDD")
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }

    fun queryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}