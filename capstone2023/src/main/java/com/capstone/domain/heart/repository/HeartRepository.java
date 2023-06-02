package com.capstone.domain.heart.repository;


import com.capstone.domain.heart.entity.Heart;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HeartRepository extends JpaRepository<Heart, Long>{

	Optional<Heart> findByUserAndPost(User user, Post post);
}
