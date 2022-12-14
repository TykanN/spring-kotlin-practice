package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.Member
import dev.tykan.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {

    /**
     * 회원가입
     */

    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id!!
    }

    fun validateDuplicateMember(member: Member) {
        val findListMember = memberRepository.findByName(member.name)
        if (findListMember.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    // 회원 전체 좋회
    fun findMembers(): MutableList<Member> = memberRepository.findAll()

    fun findOne(memberId: Long): Member? = memberRepository.findOne(memberId)

    @Transactional
    fun update(id: Long, name: String) {
        val member = memberRepository.findOne(id)
        member?.name = name
    }

}