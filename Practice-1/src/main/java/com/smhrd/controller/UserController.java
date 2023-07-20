package com.smhrd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smhrd.entity.tb_admin;
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
	
	@GetMapping("/userChatIn")
	public String userChatIn(HttpServletRequest request, HttpSession session) {
	    String userNick = request.getParameter("userNick");
	    String userAges = request.getParameter("userAges");
	    System.out.println("유저컨트롤러유저닉??"+userNick);
	    // tb_user에 사용자 정보 저장
	    tb_user user = new tb_user(userNick, userAges);
	    userRepository.save(user);

	    // 세션에 사용자 닉네임 저장
	    session.setAttribute("userNick", userNick);

	    // 채팅방 페이지로 리다이렉트
	    return "redirect:/chat/room";
	}
	
//	@GetMapping("/chat/message")
//    public ResponseEntity<Map<String, String>> getUserNickName() {
//        tb_user user = userRepository.findFirstByOrderByUserSeqDesc();
//        String userNick = user != null ? user.getUserNick() : "사용자 정보 없음";
//
//        // 사용자 닉네임 값을 응답으로 반환합니다.
//        Map<String, String> response = new HashMap<>();
//        response.put("userNick", userNick);
//        return ResponseEntity.ok(response);
//    }
    
//	@GetMapping("/chat/room")
//		public String chatRoom(Model model) {
//			return "/chat/room";
//		}
//	@GetMapping("/chat/room")
//	public String chatRoom(Model model, HttpSession session) {
//	    String userNick = (String) session.getAttribute("userNick");
//	    System.out.println("챗품유저닉"+userNick);
//	    // userNick 값이 null이면 기본값으로 "사용자 정보 없음"을 사용합니다.
//	    if (userNick == null) {
//	        userNick = "사용자 정보 없음";
//	    }
//
//	    // 채팅방 페이지로 이동하면서 사용자 닉네임 값을 전달합니다.
//	    model.addAttribute("userNick", userNick);
//	    return "/chat/room";
//	}


    
//	@GetMapping("/userChatIn")
//	public String userChatIn(tb_user user, tb_chatroom chatroom) {
//		// 1. 수집한 데이터를 DB에 insert
//		userRepository.save(user);
//		// 2. 사용자식별번호 tb_chatroom 테이블에 수집
//		chatroom.setUser_seq(user.getUser_seq());
//		// 3. 사용자닉네임 tb_chatroom 테이블에 수집
//		chatroom.setUserNick(user.getUserNick());
//		// 4. tb_chatroom. user_seq, user_nick 컬럼에 insert
//		chatRoomRepository.save(chatroom);
//		// 5. tb_chatroom 테이블의 cr_seq(방 식별번호) 가져옴
//		Long chatRoomId = chatroom.getCr_seq();
//		
//		return "redirect:/chatRoom?roomId=" + chatRoomId;
//	}
	
	
//	@GetMapping("/userChatOut")
//	public String userChatOut(@RequestParam("roomId") Long roomId) {
//		// 사용자가 나가기 버튼을 클릭했을 때, user_outdate 컬럼을 현재 시간으로 업데이트
//		chatRoomRepository.updateUserOutDate(roomId);
//		
//		return "index";
//	}
	
////	관리자 로그인 시 실행되는 메소드
//	@PostMapping(value = "/userChatIn")
//	public String chatUser(HttpServletRequest request, HttpSession session) {
////		String adminId = request.getParameter("adminId");
////	    String adminPw = request.getParameter("adminPw");
//	    String userNick = request.getParameter("userNick");
//	    session.setAttribute("userNick", userNick);
////	    System.out.println("세션아이디가지고와??"+session.getId());
//		log.info("userNick = {}, userNick");
//		return userNick;
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
