package com.hshim.lottomanager.service.notice

import com.hshim.lottomanager.database.notice.repository.NoticeCommentRepository
import com.hshim.lottomanager.database.notice.repository.NoticeRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.notice.NoticeCommentRequest
import com.hshim.lottomanager.model.notice.NoticeRequest
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NoticeCommandService(
    private val noticeRepository: NoticeRepository,
    private val userQueryService: UserQueryService,
    private val noticeCommentRepository: NoticeCommentRepository,
) {
    fun init(request: NoticeRequest) {
        noticeRepository.save(request.toEntity())
    }

    fun initComment(
        id: String,
        request: NoticeCommentRequest
    ) {
        val user = userQueryService.getUser()
        val notice = noticeRepository.findByIdOrNull(id)
            ?: throw GlobalException.NOT_FOUND_NOTICE.exception
        noticeCommentRepository.save(request.toEntity(notice, user))
    }

    fun updateComment(
        id: String,
        request: NoticeCommentRequest,
    ) {
        val user = userQueryService.getUser()
        noticeCommentRepository.findByIdOrNull(id)
            ?.apply {
                if (user.id != this.user.id) throw GlobalException.IS_NOT_MY_COMMENT.exception
                request.updateTo(this)
            } ?: throw GlobalException.NOT_FOUND_NOTICE_COMMENT.exception
    }

    fun delete(id: String) = noticeRepository.deleteById(id)
    fun deleteComment(id: String) = noticeCommentRepository.deleteById(id)
}