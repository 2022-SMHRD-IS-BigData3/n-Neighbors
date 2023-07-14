package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.entity.tb_admin;
import com.smhrd.entity.tb_chatroom;
import com.smhrd.entity.tb_user;
import com.smhrd.repository.AdminRepository;
import com.smhrd.repository.ChatRoomRepository;
import com.smhrd.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller // -> POJO 파일임을 나타내는 annotation(Spring MVC와 동일함)
public class UserController {
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final ChatRoomRepository chatRoomRepository;
	
	@GetMapping("/chatRoom")
	public String chatRoom() {
		return "chatRoom";
	}
	
	@GetMapping("/userChatIn")
	public String userChatIn(tb_user user, tb_chatroom chatroom) {
		// 1. 수집한 데이터를 DB에 insert
		userRepository.save(user);
		// 2. 사용자식별번호 수집
		chatroom.setUser_seq(user.getUser_seq());
		// 3. tb_chatroom.user_seq 컬럼에 insert
		chatRoomRepository.save(chatroom);
		// 4. tb_chatroom 테이블의 cr_seq(방 식별번호) 가져옴
		Long chatRoomId = chatroom.getCr_seq();
		
		return "redirect:/chatRoom?roomId=" + chatRoomId;
	}
	
	@GetMapping("/userChatOut")
	public String userChatOut(@RequestParam("roomId") Long roomId) {
		// 사용자가 나가기 버튼을 클릭했을 때, user_outdate 컬럼을 현재 시간으로 업데이트
		chatRoomRepository.updateUserOutDate(roomId);
		
		return "index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}