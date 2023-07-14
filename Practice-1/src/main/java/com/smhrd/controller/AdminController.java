package com.smhrd.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.entity.tb_admin;
import com.smhrd.repository.AdminRepository;
import com.smhrd.repository.ChatRoomRepository;
import com.smhrd.repository.UserRepository;
import com.smhrd.service.AdminLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
//@RequestMapping("admin")
@Slf4j
public class AdminController {
	@Autowired
	private final AdminLoginService adminLoginService;
	@Autowired
	private final ChatRoomRepository chatRoomRepository;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "index";
	}
	
//	관리자 로그인 시 실행되는 메소드
	@PostMapping(value = "/adminLogin")
	public String adminLogin(HttpServletRequest request, HttpSession session) {
		String adminId = request.getParameter("adminId");
	    String adminPw = request.getParameter("adminPw");
	    session.setAttribute("adminId", adminId);
//	    System.out.println("세션아이디가지고와??"+session.getId());
		log.info("adminId = {}, adminPw = {}", adminId, adminPw);
		
		if(adminLoginService.login(adminId, adminPw).equals("Success")) {
			return "redirect:/dashboard";
		}else {
			return "redirect:/index?loginFailed=true";
		}
	}
	
//	대시보드 [현재 사용자 수][누적 사용자 수] 메소드
	@GetMapping("/dashboard")
	public String dashBoard(Model model) {
		Long activeChatUserCount = chatRoomRepository.countActiveChatrooms();
		Long activeAccChatUserCount = chatRoomRepository.countActiveAccChatrooms();
		model.addAttribute("activeChatUserCount", activeChatUserCount);
		model.addAttribute("activeAccChatUserCount", activeAccChatUserCount);
		return "dashboard";
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
};
