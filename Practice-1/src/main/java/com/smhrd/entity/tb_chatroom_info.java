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
public class tb_chatroom_info {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cr_seq")
	private Long crSeq;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_nick", referencedColumnName = "user_nick", insertable = false, updatable = false)
	private tb_user userByNick;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "talker", referencedColumnName = "user_nick", insertable = false, updatable = false)
//	private tb_user userByTalker;
	
	@Column(name = "user_nick")
	private String userNick;
	
	@Column(name = "room_id")
	private String roomId;
	
	@Column(insertable = false, columnDefinition = "datetime default now()", updatable = false)
	private Date in_date;
	
	private Date out_date;
	
	
	public tb_chatroom_info(String userNick, String roomId, Date out_date) {
        //this.userSeq = userSeq;
        this.userNick = userNick;
        this.roomId = roomId;
        this.out_date = out_date;
    }
}
