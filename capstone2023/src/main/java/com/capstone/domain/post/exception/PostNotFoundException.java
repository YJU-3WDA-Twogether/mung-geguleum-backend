package com.capstone.domain.post.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class PostNotFoundException extends CustomException{

	public PostNotFoundException() {
		super(ErrorCode.POST_NOT_FOUND_ERROR);
	}
}