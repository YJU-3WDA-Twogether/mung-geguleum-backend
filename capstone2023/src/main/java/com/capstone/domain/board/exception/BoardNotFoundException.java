package com.capstone.domain.board.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class BoardNotFoundException extends CustomException{

	public BoardNotFoundException() {
		super(ErrorCode.BOARD_NOT_FOUND_ERROR);
	}
}
