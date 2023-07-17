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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class tb_chatroom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cr_seq")
	private Long cr_seq;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", referencedColumnName = "user_seq", insertable = false, updatable = false)
	private tb_user user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_nick", referencedColumnName = "user_nick", insertable = false, updatable = false)
	private tb_user userByNick;
	
	@Column(name = "user_seq")
	private Long user_seq;
	
	@Column(name = "user_nick")
	private String userNick;
	
	@Column(insertable = false, columnDefinition = "datetime default now()", updatable = false)
	private Date user_indate;
	
	@Column(name = "user_outdate")
	private Date userOutDate;
	
	public tb_chatroom(Long user_seq, String userNick) {
		this.user_seq = user_seq;
		this.userNick = userNick;
	}
	
	// 기타 Getter, Setter, 생성자 등의 코드
}
