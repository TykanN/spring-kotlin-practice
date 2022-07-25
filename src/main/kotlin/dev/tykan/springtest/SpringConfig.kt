package dev.tykan.springtest

import dev.tykan.springtest.repository.MemberRepository
import dev.tykan.springtest.repository.MemoryMemberRepository
import dev.tykan.springtest.service.MemberService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {

    @Bean
    fun memberService(): MemberService {
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository(): MemberRepository {
        return MemoryMemberRepository()
    }
}