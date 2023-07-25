package com.smhrd.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.entity.ChatRoom;
import com.smhrd.entity.tb_chatroom_info;
import com.smhrd.entity.tb_user;
import com.smhrd.repository.ChatRoomInfoRepository;
import com.smhrd.repository.ChatRoomRepository;
import com.smhrd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;
	private final ChatRoomInfoRepository chatRoomInfoRepository;
	
//	@GetMapping("/logout")
//	public String logOut() {
//		return "index";
//	}
	
//	@GetMapping("/index")
//	public String index() {
//		return "index";
//	}
	
	// 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }
    
    // 채팅 리스트 화면
    @GetMapping("/roomdetail_sub")
    public String roomdetail_sub(Model model) {
    	return "/chat/roomdetail_sub";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    // 채팅방 입장 화면
    //@DeleteMapping("/{talker}")
    @GetMapping("/room/enter/{roomId}&{talker}&{userAges}")
    public String roomDetail(Model model, @PathVariable String roomId, @PathVariable String talker, @PathVariable String userAges, HttpSession session, tb_user user, tb_chatroom_info chatroom_info) {
    	System.out.println("룸 아이디??"+roomId);
    	System.out.println("유저??"+talker);
    	System.out.println("나이??"+userAges);
    	model.addAttribute("roomId", roomId);
    	String userNick = talker;
    	String userAges2 = userAges;
    	user.setUserNick(userNick);
    	user.setUserAges(userAges2);
    	userRepository.save(user);
    	chatroom_info.setUserNick(user.getUserNick());
    	chatroom_info.setRoomId(roomId);
    	chatRoomInfoRepository.save(chatroom_info);
    	//model.addAttribute("talker", talker);
    	//user.setUserNick(talker);
    	//System.out.println("토커 누구야??"+talker);
        //user.setUserNick(userNick);
        //System.out.println("유저닉 누구??"+userNick);
    	
//    	System.out.println("유저??" + user);
//    	System.out.println("유저 닉 들어옴??" + userNick);
//        userRepository.save(user);
//        session.setAttribute("userNick", userNick);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
    
    // '대화방 나가기' 버튼을 클릭한 경우
    @Transactional
    @PostMapping("/room/exit")
    public String exitRoom(@RequestParam String roomId, @RequestParam String userNick) {
        tb_chatroom_info chatroomInfo = chatRoomInfoRepository.findByRoomId(roomId, userNick);
        System.out.println("유저닉네임??"+userNick);
        if (chatroomInfo != null && userNick.equals(chatroomInfo.getUserNick())) {
            chatroomInfo.setOut_date(new Date());
            chatRoomInfoRepository.save(chatroomInfo);
        }
        return "redirect:/chat/room";
    }
    
    
    
    
}
