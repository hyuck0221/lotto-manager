package com.hshim.lottomanager.setting.socket

import com.hshim.lottomanager.model.socket.SimulationEventModel
import io.autocrypt.sakarinblue.universe.util.CommonUtil.convertObject2JsonString
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

@Component
class SessionManager {
    fun addSession(session: WebSocketSession) {
        // TODO 처리중인 프로세스 있는지 확인 후 있으면 처리중인 프로세스 연결
        send(session, SimulationEventModel.SessionInfo(session.id, chats))
    }

    fun removeSession(groupId: String, session: WebSocketSession) {
        // TODO 처리중인 프로세스 있는지 검사
    }

    fun send(session: WebSocketSession, event: BaseEventModel) {
        if (session.isOpen) session.sendMessage(TextMessage(convertObject2JsonString(event)))
    }
}