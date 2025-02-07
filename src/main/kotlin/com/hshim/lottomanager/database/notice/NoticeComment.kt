package com.hshim.lottomanager.database.notice

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "notice_comment")
class NoticeComment(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    val notice: Notice,

    @Column(nullable = false, columnDefinition = "TEXT")
    var comment: String,

    @Column(nullable = false)
    var anonymous: Boolean,
) : BaseTimeEntity()