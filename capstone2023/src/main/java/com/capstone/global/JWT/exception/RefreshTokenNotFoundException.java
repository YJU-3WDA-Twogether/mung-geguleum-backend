package com.capstone.global.JWT.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class RefreshTokenNotFoundException extends CustomException{
	public RefreshTokenNotFoundException() {
		super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
	}
}
