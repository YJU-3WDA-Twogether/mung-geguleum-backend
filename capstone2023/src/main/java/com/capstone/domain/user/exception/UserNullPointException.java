package com.capstone.domain.user.exception;



import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class UserNullPointException extends CustomException{

	public UserNullPointException() {
		super(ErrorCode.USER_NULL_POINT_ERROR);
	}
}