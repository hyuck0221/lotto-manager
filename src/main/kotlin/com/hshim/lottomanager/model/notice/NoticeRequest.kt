package com.hshim.lottomanager.model.notice

import com.hshim.lottomanager.database.notice.Notice

class NoticeRequest(
    val title: String,
    val content: String,
    val enableComment: Boolean,
) {
    fun toEntity() = Notice(
        title = title,
        content = content,
        enableComment = enableComment,
    )
}