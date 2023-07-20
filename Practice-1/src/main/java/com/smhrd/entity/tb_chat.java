package com.smhrd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class tb_chat {
	
	public enum MessageType {
        ENTER, TALK  // 채팅 메시지의 유형을 나타내는 방식
        			// TALK : 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_seq")
	private Long chatSeq;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_seq", referencedColumnName = "user_seq", insertable = false, updatable = false)
//	private tb_user userBySeq;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "talker", referencedColumnName = "user_nick", insertable = false, updatable = false)
//	private tb_user userByTalker;
	
//	@Column(name = "user_seq")
//	private Long userSeq;
	
	@Column(name = "talker")
	private String talker;
	
	private String message;
	
	@Column(insertable = false, columnDefinition = "datetime default now()", updatable = false)
	private Date talk_at;
	
	private MessageType type;
	
	@Column(name = "room_id")
	private String roomId;
	
	public tb_chat(String talker, String message, String roomId) {
        //this.userSeq = userSeq;
        this.talker = talker;
        this.message = message;
        this.roomId = roomId;
    }
}
