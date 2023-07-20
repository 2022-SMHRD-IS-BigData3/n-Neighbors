package com.smhrd.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.repository.ChatMessageRepository;

import com.smhrd.entity.tb_chat;
import com.smhrd.entity.tb_user;
import com.smhrd.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatMessageRepository chatMessageRepository;

	@MessageMapping("/chat/message")
    public void enter(tb_chat message) {
		System.out.println("토커??"+message.getTalker());
    	System.out.println("메시지??"+message.getMessage());
    	System.out.println("메시지타입????"+message.getType());
    	if(message.getType().equals(tb_chat.MessageType.ENTER)) {
    		message.setMessage(message.getTalker()+"님이 입장하셨습니다.");
    	}
    	//message.setUserSeq(196L);
    	chatMessageRepository.save(message);
    	sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(),message);
    }
//    public void enter(tb_chat chat, @Headers Map<String, Object> headers) {
//        String userNick = (String) headers.get("userNick");
//        System.out.println("메세지컨트롤러유저닉네임" + userNick);
//        if (tb_chat.MessageType.ENTER.equals(chat.getType()) && userNick != null) {
//            chat.setMessage(userNick + "님이 입장하셨습니다.");
//            chat.setUserNick(userNick);
//            
//            // tb_user 테이블에서 해당 사용자 닉네임을 찾아서 userSeq 값을 가져옵니다.
//            tb_user user = userRepository.findByUserNick(userNick);
//            if (user != null) {
//                chat.setUserSeq(user.getUserSeq());
//            } else {
//                // 사용자를 찾지 못한 경우 예외 처리를 할 수 있습니다.
//                // 여기서는 기본값으로 0을 설정합니다. 이는 특별한 처리를 위한 것이므로,
//                // 사용자에 맞게 변경해주시기 바랍니다.
//                chat.setUserSeq(0L);
//            }
//        } else {
//            chat.setMessage("알 수 없는 사용자가 입장하셨습니다.");
//        }
////        chat.setUserSeq(10L);
//        chat.setRoom_id("세종대화");
//        chatMessageRepository.save(chat); // db에 채팅 저장
//        System.out.println("메세지컨트롤러룸아이디??"+chat.getRoom_id());
//        sendingOperations.convertAndSend("/topic/chat/room/" + chat.getRoom_id(), chat);
//    }
}
