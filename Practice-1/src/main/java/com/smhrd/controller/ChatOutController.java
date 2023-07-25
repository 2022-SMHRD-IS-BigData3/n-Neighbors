package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smhrd.repository.ChatRoomInfoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatOutController {
	private final ChatRoomInfoRepository chatRoomInfoRepository;
	
	@GetMapping("/chatOut")
	public String chatOut(@PathVariable String roomId) {
		System.out.println("방번호 들어옴??"+roomId);
		return "redirect:/chat/room";
	}
}
