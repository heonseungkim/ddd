package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Member
import com.musinsa.study.ddd.domain.value.ReservationStatus

interface ReservationQuerydslRepository {
    fun findByMemberAndStatus(member: Member, status: ReservationStatus)
}
