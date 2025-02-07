package com.hshim.lottomanager.database.notice

import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "notice")
class Notice(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(nullable = false)
    var enableComment: Boolean,
) : BaseTimeEntity() {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "notice")
    var noticeComments: MutableList<NoticeComment> = mutableListOf()
}