package com.capstone.global.security.jwt.exception;


import com.capstone.global.exception.ErrorCode;
import com.capstone.global.exception.CustomException;

public class RefreshTokenNotFoundException extends CustomException {
	public RefreshTokenNotFoundException() {
		super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
	}
}