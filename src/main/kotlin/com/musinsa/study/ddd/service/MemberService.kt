package com.musinsa.study.ddd.service

import com.musinsa.study.ddd.domain.entity.Member
import com.musinsa.study.ddd.domain.repository.MemberRepository
import com.musinsa.study.ddd.domain.value.MemberId
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository,
) {
    fun get(
        memberId: MemberId
    ): Member = memberRepository.findById(memberId).orElseThrow { NoSuchElementException("${memberId.value} 회원은 존재하지 않습니다.") }
}
