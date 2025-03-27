package com.hshim.lottomanager.setting.socket

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class CustomWebSocketHandler(private val sessionManager: SessionManager) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionManager.addSession(session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
//        val content = message.payload
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionManager.removeSession(session)
    }
}