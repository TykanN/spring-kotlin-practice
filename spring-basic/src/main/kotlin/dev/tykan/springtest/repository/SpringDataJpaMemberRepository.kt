package dev.tykan.springtest.repository

import dev.tykan.springtest.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataJpaMemberRepository : JpaRepository<Member, Long>, MemberRepository  {
    override fun findByName(name: String): Member?
}