package com.hshim.lottomanager.database.game

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "game_log")
class GameLog(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val rank: Int,

    @Column(nullable = false)
    val point: Int,

    @Column(nullable = true, columnDefinition = "TEXT")
    val content: String?,
) : BaseTimeEntity()