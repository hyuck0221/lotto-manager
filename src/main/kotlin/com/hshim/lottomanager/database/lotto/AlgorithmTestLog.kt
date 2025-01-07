package com.hshim.lottomanager.database.lotto

import com.hshim.lottomanager.database.base.converter.IntConverter
import com.hshim.lottomanager.enums.lotto.NumberBuildAlgorithm
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime


@Entity
@Table(name = "algorithm_test_log")
class AlgorithmTestLog(
    @Id
    @Column(nullable = false)
    private val id: String = uuid(),

    @Column(nullable = false)
    val taskId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id", nullable = false)
    val lotto: Lotto,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = IntConverter::class)
    val numbers: List<Int>,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    val algorithm: NumberBuildAlgorithm,

    @Column(nullable = true, columnDefinition = "TEXT")
    val request: String?,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(nullable = false)
    var updateDate: LocalDateTime = LocalDateTime.now()

) : Persistable<String> {
    @Column(nullable = false)
    var rank: Int = setRank()

    @Column(nullable = false)
    val correctCnt: Int = numbers.count { lotto.numbers!!.contains(it) }

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
        return this.rank
    }

    override fun isNew(): Boolean = true
    override fun getId(): String = id
}