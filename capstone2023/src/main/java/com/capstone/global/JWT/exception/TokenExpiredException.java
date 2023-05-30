package com.capstone.global.JWT.exception;

import com.capstone.global.exception.ErrorCode;

public class TokenExpiredException extends TokenException {

	public TokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}
}