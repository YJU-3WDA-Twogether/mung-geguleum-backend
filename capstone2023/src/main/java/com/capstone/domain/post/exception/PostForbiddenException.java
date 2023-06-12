package com.capstone.domain.post.exception;


import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class PostForbiddenException extends CustomException{

	public PostForbiddenException() {
		super(ErrorCode.POST_FORBIDDEN_ERROR);
	}
}
