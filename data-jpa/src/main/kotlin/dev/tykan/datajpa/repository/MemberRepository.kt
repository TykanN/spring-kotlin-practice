package dev.tykan.datajpa.repository

import dev.tykan.datajpa.dto.MemberDto
import dev.tykan.datajpa.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByUsernameAndAgeGreaterThan(username: String, age:Int) : List<Member>

//    @Query(name = "Member.findByUsername")
    fun findByUsername(@Param("username") username: String) : List<Member>

    @Query("select m from Member m where m.username = :username")
    fun findUser(@Param("username") username: String) : List<Member>

    @Query("select m.username from Member m")
    fun findUsernameList(): List<String>

    @Query("select new dev.tykan.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    fun findMemberDto(): List<MemberDto>


    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: List<String>): List<Member>

    fun findListByUsername(username: String):List<Member>

    fun findMemberByUsername(username: String) :Member

    fun findOptionalByUsername(username: String) :Member?
}
