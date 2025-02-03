package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.enums.account.SendType
import com.hshim.lottomanager.enums.account.UserType
import jakarta.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @Column(nullable = false, columnDefinition = "varchar(100)")
    var id: String,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    open var userType: UserType,

    @Column(nullable = false)
    var displayName: String,

    @Column(nullable = false)
    var disable: Boolean = false,

    @Column(nullable = true)
    var profileUrl: String?,

    @Column(nullable = true)
    var email: String?,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var sendType: SendType? = email?.let { SendType.EMAIL },
) : BaseTimeEntity()