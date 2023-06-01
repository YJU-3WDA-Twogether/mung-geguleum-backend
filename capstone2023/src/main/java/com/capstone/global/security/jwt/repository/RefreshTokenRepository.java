package com.capstone.global.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.global.security.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByUno(Long uno);

	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
