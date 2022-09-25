package dev.tykan.datajpa.entity

import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Member(
    var username: String = "default name",
    var age: Int?,


) : BaseEntity(){

    constructor(username: String, age: Int, team: Team?) : this(username, age) {
        team?.let {
            changeTeam(team)
        }
    }



    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    fun changeTeam(team: Team){
        this.team = team
        team.members.add(this)
    }
}