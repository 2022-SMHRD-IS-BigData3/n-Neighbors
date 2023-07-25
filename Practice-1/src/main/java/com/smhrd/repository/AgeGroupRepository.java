package com.smhrd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.tb_admin;
import com.smhrd.entity.tb_age_group;

@Repository
public interface AgeGroupRepository extends JpaRepository<tb_age_group, Long>{   // <테이블 역할을 하는 클래스, 테이블 PK의 자료형> 
}