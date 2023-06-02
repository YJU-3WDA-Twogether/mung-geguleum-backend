package com.capstone.global.security.jwt.exception;


import com.capstone.global.exception.ErrorCode;

public class TokenInvalidException extends TokenException {

	public TokenInvalidException() {
		super(ErrorCode.TOKEN_INVALID);
	}
}
