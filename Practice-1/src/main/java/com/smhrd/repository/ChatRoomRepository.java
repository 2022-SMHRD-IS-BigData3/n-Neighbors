package com.smhrd.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chatroom;

@Repository
public interface ChatRoomRepository extends JpaRepository<tb_chatroom, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형>
	@Query("SELECT COUNT(*) FROM tb_chatroom WHERE user_outdate IS NULL")
	Long countActiveChatrooms();
	
	@Query("SELECT COUNT(*) FROM tb_chatroom")
	Long countActiveAccChatrooms();
	
	@Transactional
	@Modifying
	@Query("update tb_chatroom c set c.userOutDate = CURRENT_TIMESTAMP where c.cr_seq = :roomId")
	void updateUserOutDate(@Param("roomId") Long roomId);
}