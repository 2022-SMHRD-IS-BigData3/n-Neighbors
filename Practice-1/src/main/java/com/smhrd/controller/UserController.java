package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		chatroom.setUser_seq(user.getUser_seq());
		chatRoomRepository.save(chatroom);
		
		
		return "redirect:/chatRoom";
	}
	
	
	
	
//	@RequestMapping("/chatRoom")
//	public String chatRoom(Model model) {
//		List<User> list = repository.findAll(Sort.by(Sort.Direction.ASC, "user_id"));
//		model.addAttribute("list", list);
//		return "chatRoom";
//	}
}
