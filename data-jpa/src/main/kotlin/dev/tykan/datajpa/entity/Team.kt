package dev.tykan.datajpa.entity

import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Team(
    var name: String?,
) : BaseEntity(){
    @OneToMany(mappedBy = "team")
    var members:MutableList<Member> = mutableListOf()
}