package com.capstone.domain.file.exception;


import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class FileNotFoundException extends CustomException{

	public FileNotFoundException() {
		super(ErrorCode.FILE_NOT_FOUND_ERROR);
	}
}