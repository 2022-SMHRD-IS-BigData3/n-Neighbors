package com.smhrd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginService {
	
	private final UserRepository userRepository;
	
	// 닉네임 중복 여부 확인
	@Transactional(readOnly = true)
	public boolean checkUserNickDuplication(String userNick) {
		boolean userNickDuplicate = userRepository.existsByUserNick(userNick);
		return userNickDuplicate;
	}
}
