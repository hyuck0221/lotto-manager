package com.hshim.lottomanager.model.notice

import com.hshim.lottomanager.database.notice.NoticeComment
import io.autocrypt.sakarinblue.universe.util.DateUtil.dateToString

class NoticeCommentResponse(
    val id: String,
    val comment: String,
    val anonymous: Boolean,
    val userName: String?,
    val userProfile: String?,
    val myComment: Boolean,
    val createDate: String,
) {
    constructor(noticeComment: NoticeComment, userId: String) : this(
        id = noticeComment.id,
        comment = noticeComment.comment,
        anonymous = noticeComment.anonymous,
        userName = noticeComment.anonymous.takeIf { !it }?.let { noticeComment.user.displayName },
        userProfile = noticeComment.anonymous.takeIf { !it }?.let { noticeComment.user.profileUrl },
        myComment = userId == noticeComment.user.id,
        createDate = noticeComment.createDate.dateToString(),
    )
}