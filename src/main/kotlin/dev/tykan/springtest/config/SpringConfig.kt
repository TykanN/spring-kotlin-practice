package dev.tykan.springtest.config

import dev.tykan.springtest.repository.h2.JdbcMemberRepository
import dev.tykan.springtest.repository.MemberRepository
import dev.tykan.springtest.repository.h2.JdbcTemplateMemberRepository
import dev.tykan.springtest.repository.h2.JpaMemberRepository
import dev.tykan.springtest.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.sql.DataSource
import javax.transaction.Transactional

@Configuration
class SpringConfig {

//    @Autowired
//    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var em: EntityManager

    @Bean
    @Transactional
    fun memberService(): MemberService {
        return MemberService(memberRepository())
    }

    @Bean
    fun memberRepository(): MemberRepository {
        return JpaMemberRepository(em)
//        return JdbcTemplateMemberRepository(h2DataSource)
//        return JdbcMemberRepository(h2DataSource)
//        return MemoryMemberRepository()
    }
}