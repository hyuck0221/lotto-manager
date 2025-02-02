package com.hshim.lottomanager.database.game

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "daily_check")
class DailyCheck(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    var amount: Int = 1000,
) : BaseTimeEntity()