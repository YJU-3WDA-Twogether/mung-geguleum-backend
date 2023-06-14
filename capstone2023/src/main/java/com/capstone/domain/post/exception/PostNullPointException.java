package com.capstone.domain.post.exception;


import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class PostNullPointException extends CustomException{

	public PostNullPointException() {
		super(ErrorCode.POST_NULL_POINT_ERROR);
	}
}