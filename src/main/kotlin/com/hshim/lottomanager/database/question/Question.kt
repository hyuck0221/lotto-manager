package com.hshim.lottomanager.database.question

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.base.BaseTimeEntity
import com.hshim.lottomanager.enums.question.QuestionType
import io.autocrypt.sakarinblue.universe.util.CommonUtil.uuid
import jakarta.persistence.*


@Entity
@Table(name = "question")
class Question(
    @Id
    @Column(nullable = false)
    val id: String = uuid(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    var type: QuestionType,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    var content: String?,

    @Column(nullable = false)
    var isReplyAlert: Boolean,

    @Column(nullable = true, columnDefinition = "TEXT")
    var reply: String? = null,

    @Column(nullable = false)
    val replyId: String = uuid(),
) : BaseTimeEntity()