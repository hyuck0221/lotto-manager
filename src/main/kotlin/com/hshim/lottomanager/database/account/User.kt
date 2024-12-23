package com.hshim.lottomanager.database.account

import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.enums.account.UserType
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
class User(
    @Id
    @Column(nullable = false, columnDefinition = "char(32)")
    var id: String = uuid(),

    @Column(
        nullable = false,
        name = "user_type",
        insertable = false,
        updatable = false,
        columnDefinition = "varchar(15)"
    )
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
) : BaseTimeEntity()