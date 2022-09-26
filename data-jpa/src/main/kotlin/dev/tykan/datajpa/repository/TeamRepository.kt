package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, Long> {
}