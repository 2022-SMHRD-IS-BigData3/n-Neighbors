package com.smhrd.controller;

import com.smhrd.repository.ChatMessageRepository;
import com.smhrd.repository.ChatRoomInfoRepository;
import com.smhrd.service.DataService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {
	
	@Autowired
    private ChatRoomInfoRepository chatRoomInfoRepository;
	@Autowired
	private ChatMessageRepository chatRepository;
	

    @GetMapping("/userAgesCount")
    public List<Map<String, Object>> getChartData() {
        return chatRoomInfoRepository.getAgeGroupUserCount();
    }
    
    @GetMapping("/ageGroupFwordCount")
    public List<Map<String, Object>> getAgeGroupFwordCount() {
        return chatRoomInfoRepository.getAgeGroupFwordCount();
    }
    
//    public List<Map<String, Object>> getFwordCount(){
//    	return chatRepository;
//    }
    
}