package com.hshim.lottomanager.database.lotto

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.database.converter.IntConverter
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "lotto_number")
class LottoNumber(
    @Id
    @Column(nullable = false, columnDefinition = "char(32)")
    var id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id", nullable = false)
    var lotto: Lotto,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = IntConverter::class)
    val numbers: List<Int> = emptyList(),

) : BaseTimeEntity() {
    @Column(nullable = true)
    var rank: Int? = when (lotto.isOpen) {
        true -> setRank()
        false -> null
    }

    fun setRank(): Int {
        val cnt = numbers.count { lotto.numbers?.contains(it) == true }
        this.rank = when {
            cnt == 6 -> 1
            cnt == 5 && numbers.contains(lotto.bonusNumber) -> 2
            cnt == 5 -> 3
            cnt == 4 -> 4
            cnt == 3 -> 5
            else -> 0
        }
        return this.rank!!
    }
}