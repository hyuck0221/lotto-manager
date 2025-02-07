package com.hshim.lottomanager.database.notice.repository

import com.hshim.lottomanager.database.notice.NoticeComment
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeCommentRepository : JpaRepository<NoticeComment, String> {
}