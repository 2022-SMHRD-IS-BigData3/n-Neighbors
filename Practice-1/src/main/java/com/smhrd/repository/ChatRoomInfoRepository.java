package com.smhrd.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chatroom_info;

@Repository
public interface ChatRoomInfoRepository extends JpaRepository<tb_chatroom_info, Long>{

	@Query("SELECT c FROM tb_chatroom_info c WHERE c.roomId = :roomId AND c.userNick = :userNick")
	tb_chatroom_info findByRoomId(@Param("roomId") String roomId, @Param("userNick") String userNick);
	// 현재 사용자 수
	@Query("SELECT COUNT(c) FROM tb_chatroom_info c WHERE c.out_date IS NULL")
	Long countNowUserCount();
	// 누적 사용자 수
	@Query("SELECT COUNT(*) FROM tb_chatroom_info")
	Long countNowUserAccCount();
	// 연령대별 사용자 횟수
    @Query("SELECT c.ageGroup AS ageGroup, COUNT(a.crSeq) AS userCount " +
            "FROM tb_chatroom_info a, tb_user b, tb_age_group c " +
            "WHERE b.userAges = c.ageId AND a.userNick = b.userNick " +
            "GROUP BY c.ageGroup")
    List<Map<String, Object>> getAgeGroupUserCount();
    // 연령대별 비속어 횟수
    @Query("SELECT c.ageGroup AS ageGroup, count(a.message) AS fwordCount " +
            "FROM tb_chat a, tb_user b, tb_age_group c " +
            "WHERE a.talker = b.userNick " +
            "AND b.userAges = c.ageId " +
            "AND a.type = 2 " +
            "GROUP BY c.ageGroup")
     List<Map<String, Object>> getAgeGroupFwordCount();

}