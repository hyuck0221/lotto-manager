package com.hshim.lottomanager.model.notice

import com.hshim.lottomanager.database.notice.Notice
import io.autocrypt.sakarinblue.universe.util.DateUtil.dateToString

class NoticeResponse(
    val id: String,
    val title: String,
    val content: String,
    val enableComment: Boolean,
    val commentCnt: Int,
    val createDate: String,
) {
    constructor(notice: Notice) : this(
        id = notice.id,
        title = notice.title,
        content = notice.content,
        enableComment = notice.enableComment,
        commentCnt = notice.noticeComments.size,
        createDate = notice.createDate.dateToString(),
    )
}