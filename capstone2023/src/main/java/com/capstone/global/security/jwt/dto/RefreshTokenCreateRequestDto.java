package com.capstone.global.security.jwt.dto;



import com.capstone.global.security.jwt.entity.RefreshToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenCreateRequestDto {
	private Long uno;
	private String refreshToken;

	@Builder
	public RefreshTokenCreateRequestDto(Long uno, String refreshToken) {
		this.uno = uno;
		this.refreshToken = refreshToken;
	}

	public RefreshToken toEntity() {
		return RefreshToken.builder()
			.uno(uno)
			.refreshToken(refreshToken)
			.build();
	}
}
