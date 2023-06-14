package com.capstone.domain.log.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class LogNotFoundException extends CustomException{

	public LogNotFoundException() {
		super(ErrorCode.LOG_NOT_FOUND_ERROR);
	}
}
