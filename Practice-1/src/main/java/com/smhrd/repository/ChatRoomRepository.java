package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chatroom;

@Repository
public interface ChatRoomRepository extends JpaRepository<tb_chatroom, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형>
	@Query("SELECT COUNT(*) FROM tb_chatroom WHERE user_outdate IS NULL")
	Long countActiveChatrooms();
	@Query("SELECT COUNT(*) FROM tb_chatroom")
	Long countActiveAccChatrooms();
}