package com.capstone.domain.log.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.domain.log.entity.Log;

public interface LogRepository extends JpaRepository<Log ,Long>{

	@Query("SELECT l FROM Log l WHERE l.user.uno = :uno AND DATE_FORMAT(l.regDate, '%Y-%m') LIKE %:date%")
	List<Log> findByUserAndRegDate(@Param("uno") Long uno, @Param("date") String date);
	 
	@Query("SELECT l From Log l WHERE l.user.uno = :uno AND l.logState.lsno = :lsno")
	Page<Log> findByUserAndLogState(@Param("uno") Long uno, @Param("lsno") Long lsno, Pageable pageable);
	
	@Query("SELECT l From Log l WHERE l.post.user.uno = :puno AND l.logState.lsno = :lsno")
	Page<Log> findByPuserAndLogState(@Param("puno") Long puno, @Param("lsno") Long lsno, Pageable pageable);
	
	
//	@Query("SELECT l From Log l JOIN l.post p JOIN l.user u JOIN l.post.files f WHERE l.logState.lsno = :lsno ORDER BY l.lno DESC")
//	Page<Log> findByLogState(@Param("lsno") Long lsno , Pageable pageable);
	
	@Query("SELECT l From Log l JOIN l.post p  JOIN l.user u WHERE l.logState.lsno = :lsno ORDER BY l.lno DESC")
	Page<Log> findByLogState(@Param("lsno") Long lsno , Pageable pageable);

	
}
	