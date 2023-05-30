package com.capstone.global.JWT.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {

	@Id
	private Long uno;

	@Column(nullable = false, unique = true)
	private String refreshToken;

	@Builder
	public RefreshToken(Long uno, String refreshToken) {
		this.uno = uno;
		this.refreshToken = refreshToken;
	}

	public void update(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}