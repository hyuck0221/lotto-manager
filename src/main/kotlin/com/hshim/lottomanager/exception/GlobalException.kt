package com.hshim.lottomanager.exception

import io.autocrypt.sakarinblue.universe.util.ClassUtil.classToMap
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

enum class GlobalException(
    val message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) {
    NOT_SUPPORT_OAUTH("지원하지 않는 Oauth 입니다.", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_USER("유저를 찾을 수 없습니다."),
    IS_DISABLED_USER("비활성화된 유저입니다."),
    NOT_FOUND_TIMES("로또 회 정보를 찾을 수 없습니다."),
    CAN_NOT_INIT_TIMES("등록할 수 없는 회차입니다."),
    NOT_FOUND_QUESTION("문의 정보를 찾을 수 없습니다."),
    IS_NOT_ADMIN("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN),
    CAN_NOT_DELETE_MY_ADMIN_ROLE("본인의 관리자 권한은 삭제할 수 없습니다."),
    ALREADY_DAILY_CHECK("이미 출석체크 하였습니다."),
    NOT_VERIFY_EMAIL("인증되지 않은 이메일입니다.", HttpStatus.FORBIDDEN),
    IS_NOT_LOTTO_URL("로또 URL이 아닙니다."),
    NOT_FOUND_NOTICE("공지사항을 찾을 수 없습니다."),
    NOT_FOUND_NOTICE_COMMENT("공지사항 댓글을 찾을 수 없습니다."),
    IS_NOT_MY_COMMENT("본인의 댓글이 아닙니다."),
    POINT_UNDER_ZERO("포인트가 부족합니다."),
    ;

    val exception = exception(null)
    fun exception(responseBody: Any?): ResponseStatusException {
        val message = responseBody?.classToMap()?.toString() ?: this.message
        return ResponseStatusException(status, message)
    }
}