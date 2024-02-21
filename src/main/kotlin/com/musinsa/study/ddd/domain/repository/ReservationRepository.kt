package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Reservation
import com.musinsa.study.ddd.domain.value.ReservationId
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, ReservationId>, ReservationQuerydslRepository {

}
