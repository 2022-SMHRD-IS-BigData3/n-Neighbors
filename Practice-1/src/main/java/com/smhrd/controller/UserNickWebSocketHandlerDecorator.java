package com.smhrd.controller;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class UserNickWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    public UserNickWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userNick = (String) session.getAttributes().get("userNick");
        session.getAttributes().put("userNick", userNick);
        super.afterConnectionEstablished(session);
    }
}
