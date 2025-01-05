package com.hshim.lottomanager.database.send

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "email_log")
class EmailLog(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseTimeEntity()