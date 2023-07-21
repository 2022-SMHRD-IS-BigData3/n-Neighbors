package com.smhrd.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> sessions = new HashSet<>();
    private int connectedUsers = 0;

    public int getConnectedUsers() {
        return connectedUsers;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        connectedUsers = sessions.size();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        sessions.remove(session);
        connectedUsers = sessions.size();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 메시지 핸들링 로직을 추가할 수 있습니다.
    }
}