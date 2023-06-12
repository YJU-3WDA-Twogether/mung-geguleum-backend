package com.capstone.domain.reply.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class ReplyForbiddenException extends CustomException{

	public ReplyForbiddenException() {
		super(ErrorCode.REPLY_FORBIDDEN_ERROR);
	}
}