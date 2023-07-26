package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_chat;

@Repository
public interface ChatMessageRepository extends JpaRepository<tb_chat, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형>
	tb_chat findByMessage(String message);

	// WordCloud
	List<tb_chat> findByType(tb_chat.MessageType type);

	@Query(value = "SELECT ranking, talker, message " +
            "FROM (SELECT talker, message, " +
            "             ROW_NUMBER() OVER (ORDER BY message DESC) AS ranking " +
            "      FROM (SELECT talker, COUNT(message) AS message " +
            "            FROM tb_chat " +
            "            WHERE type = 2 " +
            "            GROUP BY talker) ranked) ranked_with_ranking " +
            "WHERE ranking <= 3", nativeQuery = true)
     List<Object[]> findTop3Ranking();
     
//     @Query(value = "")
//     List<Object[]> findFword();

}