package com.hshim.lottomanager.database.lotto

import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "simulation")
class Simulation(
    @Id
    @Column(nullable = false)
    private val id: String = uuid(),

    @Column(nullable = false)
    val taskId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id", nullable = false)
    val lotto: Lotto,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    val algorithm: NumberBuildAlgorithm,

    @Column(nullable = false)
    val cnt: Int,

    @Column(nullable = false)
    var inProcess: Boolean = false,

    @Column(nullable = false)
    var finish: Boolean = false,
) : BaseTimeEntity()