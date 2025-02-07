package com.hshim.lottomanager.model.notice

import com.hshim.lottomanager.database.account.User
import com.hshim.lottomanager.database.notice.Notice
import com.hshim.lottomanager.database.notice.NoticeComment

class NoticeCommentRequest(
    val comment: String,
    val anonymous: Boolean,
) {
    fun toEntity(
        notice: Notice,
        user: User,
    ) = NoticeComment(
        user = user,
        notice = notice,
        comment = comment,
        anonymous = anonymous,
    )

    fun updateTo(noticeComment: NoticeComment) {
        noticeComment.comment = comment
        noticeComment.anonymous = anonymous
    }
}