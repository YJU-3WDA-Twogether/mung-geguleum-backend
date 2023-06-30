package com.capstone.domain.heart.repository;


import com.capstone.domain.heart.entity.Heart;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface HeartRepository extends JpaRepository<Heart, Long>{

	Optional<Heart> findByUserAndPost(User user, Post post);

	@Query("SELECT DISTINCT p, b, u FROM Heart h JOIN Post p ON(h.post.pno = p.pno) LEFT JOIN p.files JOIN p.board b JOIN p.user u WHERE h.user.uno = :uno")
	Page<Object[]> findByUserUno(Pageable pageable, Long uno);
}
