package com.hshim.lottomanager.service.notice

import com.hshim.lottomanager.database.notice.repository.NoticeRepository
import com.hshim.lottomanager.exception.GlobalException
import com.hshim.lottomanager.model.notice.NoticeCommentResponse
import com.hshim.lottomanager.model.notice.NoticeResponse
import com.hshim.lottomanager.service.account.user.UserQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NoticeQueryService(
    private val noticeRepository: NoticeRepository,
    private val userQueryService: UserQueryService,
) {
    fun getNoticePage(
        pageable: Pageable,
        search: String?,
    ): Page<NoticeResponse> {
        return when (search) {
            null -> noticeRepository.findAllPage(pageable)
            else -> noticeRepository.findAllBySearch(pageable, search)
        }.map { NoticeResponse(it) }
    }

    fun findById(id: String): NoticeResponse {
        return noticeRepository.findByIdOrNull(id)
            ?.let { NoticeResponse(it) }
            ?: throw GlobalException.NOT_FOUND_NOTICE.exception
    }

    fun findAllCommentsById(id: String): List<NoticeCommentResponse> {
        val user = userQueryService.getUser()
        return noticeRepository.findByIdOrNull(id)
            ?.let { it.noticeComments.map { noticeComment -> NoticeCommentResponse(noticeComment, user.id) } }
            ?.sortedByDescending { it.createDate }
            ?: throw GlobalException.NOT_FOUND_NOTICE.exception
    }
}