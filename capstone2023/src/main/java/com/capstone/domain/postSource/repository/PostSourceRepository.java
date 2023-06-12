package com.capstone.domain.postSource.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capstone.domain.postSource.entity.PostSource;

public interface PostSourceRepository extends JpaRepository<PostSource,Long>{
	
	@Query("SELECT ps FROM PostSource ps WHERE ps.post.pno = :pno")
	List<PostSource> findAllByPno(Long pno);


//	  @Query("SELECT distinct t FROM Tag t JOIN t.post p JOIN p.user u")
//	    List<PostSource> findTagWithPostAndUser();
//	  
//	  
//	  @Query("SELECT distinct t.post.pno, t.log.lno FROM Tag t")
//	  List<Object[]> findPostAndLog();
//	  
//	  @Query("SELECT distinct t, p, l FROM Tag t JOIN t.post p JOIN t.log l")
//	  List<Object[]> findAllWithPostAndLog(); 

}
