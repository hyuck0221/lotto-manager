package com.hshim.lottomanager.database.account.email

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*
import java.util.Random

@Entity
@Table(name = "email_verify")
class EmailVerify(
    @Id
    @Column(nullable = false)
    var id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    val user: User?,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = true)
    var code: String?,

    @Column(nullable = false)
    var verify: Boolean,
) : BaseTimeEntity() {
    constructor(
        user: User,
        email: String,
    ) : this(
        user = user,
        email = email,
        code = String.format("%06d", Random().nextInt(1000000)),
        verify = false,
    )

    constructor(user: User): this (
        user = user,
        email = user.email!!,
        code = null,
        verify = true,
    )
}