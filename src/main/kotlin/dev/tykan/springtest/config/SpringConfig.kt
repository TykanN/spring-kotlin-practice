package dev.tykan.springtest.config

import dev.tykan.springtest.repository.h2.JdbcMemberRepository
import dev.tykan.springtest.repository.MemberRepository
import dev.tykan.springtest.repository.h2.JdbcTemplateMemberRepository
import dev.tykan.springtest.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class SpringConfig {

    @Autowired
    @Qualifier("h2DataSource")
    private lateinit var h2DataSource: DataSource

    @Bean
    fun memberService(): MemberService {
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository(): MemberRepository {
        return JdbcTemplateMemberRepository(h2DataSource)
//        return JdbcMemberRepository(h2DataSource)
//        return MemoryMemberRepository()
    }
}