package com.capstone.global.security.jwt.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;
import com.capstone.global.security.jwt.JwtAuthentication;
import com.capstone.global.security.jwt.JwtAuthenticationToken;
import com.capstone.global.security.jwt.JwtTokenProvider;
import com.capstone.global.security.jwt.dto.TokenInfo;
import com.capstone.global.security.jwt.dto.TokenReIssueResponseDto;
import com.capstone.global.security.jwt.entity.RefreshToken;
import com.capstone.global.security.jwt.exception.RefreshTokenNotFoundException;
import com.capstone.global.security.jwt.repository.RefreshTokenRepository;
import com.capstone.global.util.CookieUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Value("${jwt.expire-seconds.access-token}")
	int accessTokenExpireSeconds;
	@Value("${jwt.expire-seconds.refresh-token}")
	int refreshTokenExpireSeconds;

	@Transactional
	public TokenReIssueResponseDto reIssueTokens(String token) {

		jwtTokenProvider.validateToken(token);

		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
			.orElseThrow(() -> new RefreshTokenNotFoundException());

		User user = userRepository.findByUno(refreshToken.getUno())
			.orElseThrow(() -> new UserNotFoundException());

		String reIssuedAccessToken = reIssueAccessToken(user.getUno(),user.getUid(),user.getNickname(), user.getUserGrade().getGname(), user.getFile().getFpath());
		String reIssuedRefreshToken = reIssueRefreshToken(user.getUno());

		String accessTokenCookie = createAccessTokenCookie(reIssuedAccessToken);
		String refreshTokenCookie = createRefreshTokenCookie(reIssuedRefreshToken);

		return new TokenReIssueResponseDto(accessTokenCookie, refreshTokenCookie);

	}

	public String reIssueAccessToken(Long uno,String uid , String nickname, String role,String fpath) {
		return jwtTokenProvider.createAccessToken(uno,uid,nickname, role, fpath);
	}

	@Transactional
	public String reIssueRefreshToken(Long uno) {
		String token = jwtTokenProvider.createRefreshToken();
		RefreshToken reIssuedRefreshToken = new RefreshToken(uno, token);

		return refreshTokenRepository.save(reIssuedRefreshToken).getRefreshToken();
	}

	private String createAccessTokenCookie(String accessToken) {
		return CookieUtils.addCookie("accessTokenCookie", accessToken, accessTokenExpireSeconds);
	}

	private String createRefreshTokenCookie(String refreshToken) {
		return CookieUtils.addCookie("refreshTokenCookie", refreshToken, refreshTokenExpireSeconds);
	}

	public Optional<String> getAccessToken(HttpServletRequest request) {
		String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);
		if (accessToken != null) {
			jwtTokenProvider.validateToken(accessToken);
		}
		return Optional.ofNullable(accessToken);
	}

	public JwtAuthenticationToken getAuthentication(String accessToken) {
		System.out.println("하이");
		Claims claims = jwtTokenProvider.getClaims(accessToken);

		Long uno = claims.get("uno", Long.class);
		String uid = claims.get("uid", String.class);
		String nickname = claims.get("nickname",String.class);
		String role = claims.get("role", String.class);
		String fpath = claims.get("fpath", String.class);
		System.out.println(role);
		JwtAuthentication principal = new JwtAuthentication(accessToken, uno,uid,nickname,role,fpath);
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		System.out.println(authorities.get(0));
		return new JwtAuthenticationToken(principal, null, authorities);
	}
	@Transactional
	public TokenInfo generateToken(Authentication authentication) {
		System.out.println(authentication.getName() +" 테스트입니다..");
	
		User user = userRepository.findByUid(authentication.getName())
				.orElseThrow(() -> new UserNotFoundException());
		if(user.getUserGrade().getGname().equals("DROP") || user.getUserGrade().getGname().equals("BEN")) {
			throw new UserNotFoundException();
		}
		System.out.println("user 아이디"+user.getUid());
		String refreshToken = jwtTokenProvider.createRefreshToken();
		String accessToken = jwtTokenProvider.createAccessToken(user.getUno(),user.getUid(),user.getNickname(), user.getUserGrade().getGname(), user.getFile().getFpath());
		jwtTokenProvider.saveRefreshToken(user.getUno(), refreshToken);
		
		  return TokenInfo.builder()
	                .grantType("Bearer")
	                .accessToken(accessToken)
	                .refreshToken(refreshToken)
	                .build();
	}
	
	@Transactional
	public TokenInfo deleteToken (Long uno) {
		RefreshToken token = refreshTokenRepository.findById(uno).orElseThrow( () -> new RefreshTokenNotFoundException());
		refreshTokenRepository.deleteById(uno);
		  return TokenInfo.builder()
	                .grantType(null)
	                .accessToken(null)
	                .refreshToken(null)
	                .build();
		
	}

}
