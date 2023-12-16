package com.spacewalk.domain.product

import com.spacewalk.audit.AuditingDomain
import com.spacewalk.domain.order.Order
import javax.persistence.*

@Entity
class Product (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val productName: String,
    val description: String,
    val price: Int,
    val stock: Int,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "order_id")
    val order: Order? = null

) : AuditingDomain() {



}