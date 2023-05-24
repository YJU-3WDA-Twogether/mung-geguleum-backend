package com.capstone.domain.logState.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class LogStateNotFoundException extends CustomException{

	public LogStateNotFoundException() {
		super(ErrorCode.LOGSTATE_NOT_FOUND_ERROR);
	}
}