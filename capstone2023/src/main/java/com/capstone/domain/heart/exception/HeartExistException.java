package com.capstone.domain.heart.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class HeartExistException extends CustomException {
    public HeartExistException() {
        super(ErrorCode.HEART_ALREADY_EXIST);
    }
}
