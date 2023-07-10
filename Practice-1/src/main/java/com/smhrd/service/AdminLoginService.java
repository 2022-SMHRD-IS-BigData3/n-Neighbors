package com.smhrd.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smhrd.entity.tb_admin;
import com.smhrd.repository.AdminRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLoginService {
	
	private final AdminRepository adminRepository;
	
	public String login(String adminId, String adminPw) {
		Optional<tb_admin> adminOptional = adminRepository.findByAdminId(adminId);
		if(adminOptional.isPresent()) {
			tb_admin admin = adminOptional.get();
			log.info("db id = {}, input id = {}, db password = {}, input password = {}" , admin.getAdminId(),adminId, admin.getAdminPw(), adminPw);
			if(admin.getAdminPw().equals(adminPw)) {
				return "Success";
			}
		}
		return "Failed";
	}
	
	
	
	
	
	
}
