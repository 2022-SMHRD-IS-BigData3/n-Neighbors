package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_user;

@Repository
public interface UserRepository extends JpaRepository<tb_user, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형> 
	boolean existsByUserNick(String userNick);

	tb_user findFirstByOrderByUserSeqDesc();

	tb_user findByUserNick(String userNick);
}