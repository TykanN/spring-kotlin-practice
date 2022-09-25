package dev.tykan.datajpa.entity

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity {
    @Id
    @GeneratedValue
    var id: Long? = null
}