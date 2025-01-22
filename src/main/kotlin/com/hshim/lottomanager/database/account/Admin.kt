package com.hshim.lottomanager.database.account

import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "admin")
class Admin(
    @Id
    @Column(nullable = false, columnDefinition = "char(32)")
    var id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
)