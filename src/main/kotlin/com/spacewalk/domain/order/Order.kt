package com.spacewalk.domain.order

import com.spacewalk.domain.user.User
import com.spacewalk.domain.product.Product
import javax.persistence.*

@Table(name = "orders")
@Entity
class Order (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinColumn(name = "user_id")
    val user: User? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    val productList: MutableList<Product> = mutableListOf()

) {
}