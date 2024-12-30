package com.hshim.lottomanager.database.lotto

import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.database.converter.IntConverter
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "lotto")
class Lotto(
    @Id
    @Column(nullable = false)
    var times: Int,

    @Column(nullable = true)
    var openDate: LocalDate? = null,

    @Column(nullable = false)
    var isOpen: Boolean = false,

    @Column(nullable = true, columnDefinition = "varchar(255)")
    @Convert(converter = IntConverter::class)
    var numbers: List<Int>? = null,

    @Column(nullable = true)
    var bonusNumber: Int? = null,

    @Column(nullable = true)
    var totalPrize: Long? = null,

    @Column(nullable = true)
    var firstWinnerPrize: Long? = null,

    @Column(nullable = true)
    var winCnt: Int? = null,
) : BaseTimeEntity()