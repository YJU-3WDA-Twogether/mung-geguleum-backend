package com.capstone.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.domain.user.entity.UserGrade;


public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {
	Optional<UserGrade> findByGname(String gname);
}

