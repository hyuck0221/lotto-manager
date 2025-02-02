package com.hshim.lottomanager.database.game

import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "point_log")
class PointLog(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    val point: Point,

    @Column(nullable = false)
    val changedAmount: Int,

    @Column(nullable = false)
    val currentAmount: Int,
) : BaseTimeEntity()