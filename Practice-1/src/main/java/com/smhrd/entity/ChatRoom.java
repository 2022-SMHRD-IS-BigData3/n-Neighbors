package com.smhrd.entity;

import java.util.UUID;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
	private String roomId;
    private String roomName;


    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom(); 
        room.roomId = UUID.randomUUID().toString();  // 생성된 채팅방 id를 문자열로 랜덤으로 생성.
        room.roomName = name;
        return room;
    }
}
