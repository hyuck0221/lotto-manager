package com.hshim.lottomanager.api.notice

import com.hshim.lottomanager.annotation.PublicEndpoint
import com.hshim.lottomanager.model.notice.NoticeCommentRequest
import com.hshim.lottomanager.model.notice.NoticeRequest
import com.hshim.lottomanager.model.notice.NoticeResponse
import com.hshim.lottomanager.service.notice.NoticeCommandService
import com.hshim.lottomanager.service.notice.NoticeQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notice")
class NoticeController(
    private val noticeQueryService: NoticeQueryService,
    private val noticeCommandService: NoticeCommandService,
) {
    @GetMapping
    @PublicEndpoint
    fun noticePage(
        @RequestParam(required = true) page: Int,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) sort: String?,
    ): Page<NoticeResponse> {
        val sortSplit = sort?.split(",") ?: emptyList()
        val pageable = PageRequest.of(
            page, 10,
            when (sortSplit.isEmpty()) {
                true -> Sort.unsorted()
                false -> Sort.by(Sort.Direction.valueOf(sortSplit[1].uppercase()), sortSplit[0])
            },
        )
        return noticeQueryService.getNoticePage(pageable, search)
    }

    @GetMapping("/{id}")
    @PublicEndpoint
    fun notice(@PathVariable id: String) = noticeQueryService.findById(id)

    @GetMapping("/{id}/comments")
    fun noticeComments(@PathVariable id: String) = noticeQueryService.findAllCommentsById(id)

    @PostMapping
    fun init(@RequestBody request: NoticeRequest) = noticeCommandService.init(request)

    @PostMapping("/{id}/comment")
    fun initComment(
        @PathVariable id: String,
        @RequestBody request: NoticeCommentRequest,
    ) = noticeCommandService.initComment(id, request)

    @PutMapping("/comment/{id}")
    fun updateComment(
        @PathVariable id: String,
        @RequestBody request: NoticeCommentRequest,
    ) = noticeCommandService.updateComment(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = noticeCommandService.delete(id)

    @DeleteMapping("/comment/{id}")
    fun deleteComment(@PathVariable id: String) = noticeCommandService.deleteComment(id)
}