package com.capstone.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.global.security.jwt.dto.RefreshTokenCreateRequestDto;
import com.capstone.global.security.jwt.exception.TokenExpiredException;
import com.capstone.global.security.jwt.exception.TokenInvalidException;
import com.capstone.global.security.jwt.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {
	private final String secretKey;
	private final long accessTokenExpireSeconds;
	private final long refreshTokenExpireSeconds;
	private final RefreshTokenRepository refreshTokenRepository;

	private final String bearerType = "Bearer ";

	@Value("${jwt.header.access-token}")
	String accessTokenHeader;

	@Value("${jwt.header.refresh-token}")
	String refreshTokenHeader;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.expire-seconds.access-token}") long accessTokenExpireSeconds,
		@Value("${jwt.expire-seconds.refresh-token}") long refreshTokenExpireSeconds,
		RefreshTokenRepository refreshTokenRepository) {
		this.secretKey = secretKey;
		this.accessTokenExpireSeconds = accessTokenExpireSeconds;
		this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String createAccessToken(Long uno,String uid , String nickname, String role) {
		Map<String, Object> claims = Map.of("uno", uno, "uid",uid ,"nickname" ,nickname ,"role", role);
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpireSeconds * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + refreshTokenExpireSeconds * 1000L);
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessTokenHeader))
			.filter(accessToken -> accessToken.startsWith(bearerType))
			.map(accessToken -> accessToken.replace(bearerType, ""));
	}

	@Transactional
	public void updateRefreshToken(Long uno, String refreshToken) {
		refreshTokenRepository.findByUno(uno)
			.ifPresentOrElse(
				token -> token.update(refreshToken),
				() -> saveRefreshToken(uno, refreshToken)
			);
	}

	@Transactional
	public void saveRefreshToken(Long uno, String refreshToken) {
		RefreshTokenCreateRequestDto refreshTokenCreateRequestDto = RefreshTokenCreateRequestDto.builder()
			.uno(uno)
			.refreshToken(refreshToken)
			.build();
		refreshTokenRepository.save(refreshTokenCreateRequestDto.toEntity());
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (JwtException | IllegalArgumentException e) {
			throw new TokenInvalidException();
		}
	}

}
