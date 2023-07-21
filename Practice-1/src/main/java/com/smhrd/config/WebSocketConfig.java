package com.smhrd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

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
    
    // flask bean
    @Bean
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
}   


