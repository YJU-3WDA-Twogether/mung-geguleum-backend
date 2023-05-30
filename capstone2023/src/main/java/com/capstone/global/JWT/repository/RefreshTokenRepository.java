package com.capstone.global.JWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.global.JWT.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByUno(Long uno);

	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
