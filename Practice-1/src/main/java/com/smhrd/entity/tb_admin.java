package com.smhrd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class tb_admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "admin_id")
	private String adminId;
	
	@Column(name = "admin_pw")
	private String adminPw;

	@Column(insertable = false, columnDefinition = "datatime default now()" , updatable = false)
	private Date admin_joindate;
	
	@Column(name = "admin_memo")
	private String admin_memo;

	@Column(name = "ROLE")
	private String role;
}
