package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsernameAndAgeGreaterThan(username: String, age:Int) : List<Member>
}
