package com.smhrd.service;

import com.smhrd.repository.ChatRoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataService {

    private final ChatRoomInfoRepository chatRoomInfoRepository;

    @Autowired
    public DataService(ChatRoomInfoRepository chatroomInfoRepository) {
        this.chatRoomInfoRepository = chatroomInfoRepository;
    }

    public List<Map<String, Object>> getAgeGroupUserCount() {
        return chatRoomInfoRepository.getAgeGroupUserCount();
    }
}