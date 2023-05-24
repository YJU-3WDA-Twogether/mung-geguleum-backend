package com.capstone.domain.user.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class UserInvalidException extends CustomException{

	public UserInvalidException() {
		super(ErrorCode.USER_INVALID_ERROR);
	}
}