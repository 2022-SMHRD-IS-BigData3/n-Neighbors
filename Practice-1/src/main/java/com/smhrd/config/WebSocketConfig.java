package com.smhrd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.smhrd.controller.UserNickHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // "/sub" 프리픽스로 시작하는 주제를 구독할 수 있도록 설정합니다.
        config.setApplicationDestinationPrefixes("/pub"); // "/pub" 프리픽스로 시작하는 주제로 메시지를 발행할 수 있도록 설정합니다.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS를 사용하여 브라우저 호환성 보장
    }
}   
//    private HandshakeInterceptor getHandshakeInterceptor() {
//        return new UserNickHandshakeInterceptor();
//    }

//@Configuration
//@EnableWebSocketMessageBroker // WebSocket 메시지 처리 활성화
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/topic");
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat").withSockJS();
//    }

