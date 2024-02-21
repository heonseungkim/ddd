package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Member
import com.musinsa.study.ddd.domain.value.ReservationStatus

class ReservationQuerydslRepositoryImpl : ReservationQuerydslRepository {
    override fun findByMemberAndStatus(
        member: Member,
        status: ReservationStatus
    ) {
    }

}
