package com.capstone.domain.postSource.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capstone.domain.postSource.entity.PostSource;

public interface PostSourceRepository extends JpaRepository<PostSource,Long>{
	
	@Query("SELECT ps FROM PostSource ps WHERE ps.post.pno = :pno")
	List<PostSource> findAllByPno(Long pno);
}
