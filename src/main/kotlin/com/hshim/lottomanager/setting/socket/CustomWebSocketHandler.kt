package com.hshim.lottomanager.setting.socket

import com.hshim.realtimeanonymouschat.database.entity.chat.Chat
import com.hshim.lottomanager.model.socket.SimulationEventModel
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class CustomWebSocketHandler(private val sessionManager: SessionManager) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionManager.addSession(groupId, session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val content = message.payload
        val chat = Chat(
            sessionId = session.id,
            content = content,
            groupId = groupId,
        )
        chatRepository.save(chat)
        sessionManager.send(groupId, SimulationEventModel.Info(session.id, content))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionManager.removeSession(groupId, session)
    }
}