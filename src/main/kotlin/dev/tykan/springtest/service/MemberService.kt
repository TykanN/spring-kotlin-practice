package dev.tykan.springtest.service

import dev.tykan.springtest.domain.Member
import dev.tykan.springtest.repository.MemberRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {
    //    회원가입
    @Transactional
    fun join(member: Member): Long {
        //같은 이름이 있는 중복 회원 X
        validateDuplicatedMember(member)
        memberRepository.save(member)
        return member.id
    }
    
    // 전체 회원 조회

    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    // 전체 회원 조회
    fun findOne(memberId: Long): Member? {
        return memberRepository.findById(memberId)
    }

    private fun validateDuplicatedMember(member: Member) {
        memberRepository.findByName(member.name)?.let {
            throw IllegalStateException("이미 존재하는 회원")
        }
    }

}