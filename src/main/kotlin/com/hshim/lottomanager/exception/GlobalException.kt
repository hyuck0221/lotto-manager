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
    ;

    val exception = exception(null)
    fun exception(responseBody: Any?): ResponseStatusException {
        val message = responseBody?.classToMap()?.toString() ?: this.message
        return ResponseStatusException(status, message)
    }
}