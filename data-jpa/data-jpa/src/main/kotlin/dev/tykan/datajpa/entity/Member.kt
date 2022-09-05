package dev.tykan.datajpa.entity

import javax.persistence.Entity

@Entity
class Member(
    var username: String = "default name",
) : BaseEntity()