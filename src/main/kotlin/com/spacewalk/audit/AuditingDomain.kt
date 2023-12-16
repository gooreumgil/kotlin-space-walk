package com.spacewalk.audit

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
open class AuditingDomain : Serializable {

    companion object {
        private const val serialVersionUID = 20231216L
    }

    @CreatedDate
    @Column(updatable = false)
    open var createdAt: LocalDateTime ?= null

    @LastModifiedDate
    open var lastModifiedAt: LocalDateTime ?= null
}