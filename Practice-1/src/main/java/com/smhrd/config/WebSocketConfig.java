package com.smhrd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메시지 처리 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
        // 엔드 포인트 설정 / 모든 출처의 요청을 허용 / 웹소켓을 직접적으로 지원하지 않는 브라우저에 대한 폴백 옵션 활성화
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	// "/queue", "/topic" 두 대상이 있는 간단한 메모리 내 메시지 브로커 활성화. 대상으로 전송된 메시지는 구독자에게 배포
        registry.enableSimpleBroker("/queue", "/topic");
        // 응용 프로그램 대상 접두사를 "/app"으로 설정
        // 접두사는 브로커 대상이 아닌 응용 프로그램별 대상을 위한 메시지를 구별하는데 사용
        registry.setApplicationDestinationPrefixes("/app");
    }
}
