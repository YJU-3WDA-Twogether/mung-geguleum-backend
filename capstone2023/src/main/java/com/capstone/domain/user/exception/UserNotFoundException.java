package com.capstone.domain.user.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException{

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND_ERROR);
	}
}
