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
public class tb_chat_fword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fuck_seq")
	private Long fuckSeq;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "chat_seq", referencedColumnName = "chat_seq", insertable = false, updatable = false)
//	private tb_chat chatBySeq;
//	
//	@Column(name = "chat_seq")
//	private Long chatSeq;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "talker", referencedColumnName = "user_nick", insertable = false, updatable = false)
//	private tb_user userByTalker;
	
//	@Column(name = "user_seq")
//	private Long userSeq;
	
	@Column(name = "talker")
	private String talker;
	
	@Column(name = "fuck_word")
	private String fuckWord;
	
	@Column(insertable = false, columnDefinition = "datetime default now()", updatable = false)
	private Date fuck_at;
	
	public tb_chat_fword(String talker, String fuckWord) {
        //this.userSeq = userSeq;
        this.talker = talker;
        this.fuckWord = fuckWord;
    }
}
