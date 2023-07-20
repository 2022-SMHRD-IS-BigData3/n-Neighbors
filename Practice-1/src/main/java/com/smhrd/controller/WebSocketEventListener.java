package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.smhrd.entity.tb_chat;
import com.smhrd.entity.tb_user;
import com.smhrd.repository.ChatMessageRepository;
import com.smhrd.repository.UserRepository;

import java.util.Date;

@Controller
public class WebSocketEventListener {

//    private final ChatMessageRepository chatMessageRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public WebSocketEventListener(ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
//        this.chatMessageRepository = chatMessageRepository;
//        this.userRepository = userRepository;
//    }
//
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public tb_chat sendMessage(@Payload tb_chat chatMessage) {
//        chatMessageRepository.save(chatMessage);
//        return chatMessage;
//    }
//
//    @MessageMapping("/chat.newUser")
//    @SendTo("/topic/public")
//    public tb_chat newUser(@Payload tb_user user) {
//        tb_chat chatMessage = new tb_chat();
//        chatMessage.setMessage("새로운 사용자가 입장했습니다.");
//        chatMessage.setTalker(user);
//        chatMessageRepository.save(chatMessage);
//        return chatMessage;
//    }
}
