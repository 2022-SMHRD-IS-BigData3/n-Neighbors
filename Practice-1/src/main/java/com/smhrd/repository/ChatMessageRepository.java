package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chat;

@Repository
public interface ChatMessageRepository extends JpaRepository<tb_chat, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형>
	tb_chat findByMessage(String message);
}