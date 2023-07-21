package com.smhrd.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.smhrd.repository.ChatMessageRepository;

import com.smhrd.entity.tb_chat;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageRepository chatMessageRepository;
    private final RestTemplate restTemplate;
    
    private final List<String> wordList = Arrays.asList("다소니", "윤슬", "미르", "가람","꽃가람","꽃내음","나릿물","너울","는개","늘해랑","늦마","다솜","닻별","돌개바람",
			"윤슬","둔치","마루","매지구름","모람모람","미리내","솔찬","아라","잎망울","부슬비","눈먼지","예그리나","비나리","늘솔길","그린나래","물비늘","푸실","달보드레하다",
			"라온","나길","띠앗머리","다빈","다흰","라온제나","모도리","마닐마닐","먼산바라기","바림","별솔","새결","벙글다","시나브로"); // 대체될 단어 리스트
    
	@MessageMapping("/chat/message")
    public void enter(tb_chat message) {
		
    	if(message.getType().equals(tb_chat.MessageType.ENTER)) {
    		message.setMessage(message.getTalker()+"님이 입장하셨습니다.");
    	}
    	// Flask API로 메시지 보내기 및 욕설 상태 수신
    			HttpHeaders headers = new HttpHeaders();
    			headers.setContentType(MediaType.APPLICATION_JSON);
    			System.out.println("들어오니?");
    			String apiUrl = "http://172.30.1.32:5000/chat"; // API URL
    			String requestBody = "{\"message\": \"" + message.getMessage() + "\"}";// 메시지 내용으로 요청 본문 만들기
    			System.out.println("여기로 왔니?");

    			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
    			ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST , requestEntity, Map.class); // post 형태로 요청보냄

    			if (responseEntity.getStatusCode().is2xxSuccessful()) { // 응답 상태 코드가 성공적일경우 
    				Map<String, Object> responseBody = responseEntity.getBody(); // 응답된 본문 매핑
    				int profanityStatus = (int) responseBody.get("profanity"); // 본문에서 욕설 상태를 추출

    				// 욕설 상태 처리 및 메시지 수정
    				if (profanityStatus == 1) {
    					chatMessageRepository.save(message);
    					String randomWord = getRandomWord();
    					message.setMessage(randomWord); // 욕설감지가 되었을 경우 list에 있는 임의의 단어 대체
    					sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(), message); // 메시지 전송
    				} else {
    					sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(), message); // 아닐 경우 사용자가 입력한
    																											// 메시지 전송
    					chatMessageRepository.save(message);
    				}

    			} else {
    				int statusCode = responseEntity.getStatusCodeValue();
    			    String errorBody = responseEntity.getBody().toString();
    			    
    			    System.out.println("Error: " + statusCode);
    			    System.out.println("Error Body: " + errorBody);
    			}

    		}

    		private String getRandomWord() { // list에서 랜덤하게 단어 선택
    			Random random = new Random();
    			int index = random.nextInt(wordList.size());
    			return wordList.get(index);
    		}
    	

}
