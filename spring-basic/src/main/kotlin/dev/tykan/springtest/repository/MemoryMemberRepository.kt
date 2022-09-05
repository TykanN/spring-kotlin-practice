package dev.tykan.springtest.repository

import dev.tykan.springtest.domain.Member
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MemoryMemberRepository : MemberRepository {

    companion object {
        @JvmStatic
        val store: MutableMap<Long, Member> = HashMap()

        @JvmStatic
        var sequence: Long = 0L
    }


    override fun save(member: Member): Member {
        member.id = ++sequence
        store[member.id] = member
        return member
    }

    override fun findById(id: Long): Member? {
        return store[id]
    }


    override fun findByName(name: String): Member? {
        return store.values.find { member -> member.name == name }
    }

    override fun findAll(): List<Member> {
        return ArrayList(store.values)
    }

    fun clearStore() {
        store.clear()
    }

}