package com.musinsa.study.ddd.domain.repository

import com.musinsa.study.ddd.domain.entity.Member
import com.musinsa.study.ddd.domain.value.MemberId
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, MemberId>
