package com.capstone.domain.user.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class UserMissingValueException extends CustomException{

	public UserMissingValueException() {
		super(ErrorCode.USER_MISSING_VALUE_ERROR);
	}
}
