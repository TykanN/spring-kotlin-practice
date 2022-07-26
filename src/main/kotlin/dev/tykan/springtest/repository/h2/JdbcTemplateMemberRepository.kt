package dev.tykan.springtest.repository.h2

import dev.tykan.springtest.domain.Member
import dev.tykan.springtest.repository.MemberRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource

class JdbcTemplateMemberRepository(dataSource: DataSource) : MemberRepository {

    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun save(member: Member): Member {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")
        val parameters: MutableMap<String, Any> = HashMap()
        parameters["name"] = member.name

        val key: Number = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
        member.id = (key.toLong())
        return member
    }

    override fun findById(id: Long): Member? {
        val result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper, id)
        return result.firstOrNull()
    }

    override fun findByName(name: String): Member? {
        val result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper, name)
        return result.firstOrNull()
    }

    override fun findAll(): List<Member> {
        return jdbcTemplate.query("select * from member", memberRowMapper)
    }

    @get:Throws(SQLException::class)
    private val memberRowMapper = RowMapper<Member> { rs: ResultSet, _: Int ->
        val member = Member()
        member.name = rs.getString("name")
        member.id = rs.getLong("id")
        member
    }


}

