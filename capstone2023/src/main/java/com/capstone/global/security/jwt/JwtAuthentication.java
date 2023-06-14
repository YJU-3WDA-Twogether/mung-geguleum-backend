package com.capstone.global.security.jwt;


import com.capstone.global.security.jwt.exception.TokenInvalidException;

import lombok.ToString;

@ToString
public class JwtAuthentication {

	public final String accessToken;
	public final Long uno;
	public final String uid;
	public final String nickname;
	public final String role;

	public JwtAuthentication(String accessToken, Long uno, String uid, String nickname, String role) {
		if (accessToken.isEmpty() || accessToken.isBlank()) {
			throw new TokenInvalidException();
		}
		if (uno <= 0 || uno == null) {
			throw new TokenInvalidException();
		}
		this.accessToken = accessToken;
		this.uno = uno;
		this.uid = uid;
		this.nickname = nickname;
		this.role = role;
	}

}