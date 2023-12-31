package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chat;
import com.smhrd.entity.tb_chat_fword;

@Repository
public interface ChatFwordMessageRepository extends JpaRepository<tb_chat_fword, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형>

}