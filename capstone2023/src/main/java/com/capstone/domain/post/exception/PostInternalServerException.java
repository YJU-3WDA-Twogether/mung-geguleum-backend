package com.capstone.domain.post.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class PostInternalServerException extends CustomException{

	public PostInternalServerException() {
		super(ErrorCode.POST_INTERNAL_SERVER_ERROR);
	}
}
