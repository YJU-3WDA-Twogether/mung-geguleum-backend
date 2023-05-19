package com.capstone.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capstone.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUno(Long uno);
	
	Optional<User> findByUid(String uid);
	
	List<User> findByUname(String uname);
	
	Optional<User> findByEmail(String email);
	
	
	Optional<User> findByUidAndPassword(String uid, String password);
	
	Optional<User> findByNickname(String nickname);
	
	Page<User> findAll(Pageable pageable);
	
	List<User> findAll();
		
}

