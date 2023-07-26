package com.smhrd.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.palette.ColorPalette;
import com.smhrd.entity.tb_admin;
import com.smhrd.entity.tb_chat;
import com.smhrd.repository.AdminRepository;
import com.smhrd.repository.ChatMessageRepository;
import com.smhrd.repository.ChatRoomInfoRepository;
import com.smhrd.repository.UserRepository;
import com.smhrd.service.AdminLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminController {
	@Autowired
	private final AdminLoginService adminLoginService;
	@Autowired
	private final ChatRoomInfoRepository chatRoomInfoRepository;
	@Autowired
	private final ChatMessageRepository chatMessageRepositoy;
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "index";
	}
	
//	관리자 로그인 시 실행되는 메소드
	@PostMapping("/adminLogin")
	public String adminLogin(HttpServletRequest request, HttpSession session) {
		String adminId = request.getParameter("adminId");
	    String adminPw = request.getParameter("adminPw");
	    session.setAttribute("adminId", adminId);
//	    System.out.println("세션아이디가지고와??"+session.getId());
		log.info("adminId = {}, adminPw = {}", adminId, adminPw);
		
		if(adminLoginService.login(adminId, adminPw).equals("Success")) {
			return "redirect:/dashBoard";
		}else {
			return "redirect:/index?loginFailed=true";
		}
	}
	
	//대시보드 [현재 사용자 수][누적 사용자 수][TOP3] 메서드
	@GetMapping("/dashBoard")
	public String dashBoard(Model model) {
		Long countNowUserCount = chatRoomInfoRepository.countNowUserCount();
		Long countNowUserAccCount = chatRoomInfoRepository.countNowUserAccCount();
		List<Object[]> top3Ranking = chatMessageRepositoy.findTop3Ranking();
		model.addAttribute("top3Ranking", top3Ranking);
		model.addAttribute("countNowUserCount", countNowUserCount);
		model.addAttribute("countNowUserAccCount", countNowUserAccCount);
		return "dashBoard";
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
};
