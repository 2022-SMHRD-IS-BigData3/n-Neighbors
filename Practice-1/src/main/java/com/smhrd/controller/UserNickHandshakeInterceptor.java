package com.smhrd.controller;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class UserNickHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // HTTP 요청에서 세션을 가져옵니다.
        HttpSession session = getSession(request);

        // 세션에서 사용자 닉네임 값을 가져와 WebSocket의 Handshake 메시지에 추가합니다.
        String userNick = (String) session.getAttribute("userNick");
        attributes.put("userNick", userNick);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Handshake 이후에 추가적인 작업을 수행할 필요가 없으면 이 메서드를 비워둡니다.
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            return servletRequest.getServletRequest().getSession();
        }
        return null;
    }
}
