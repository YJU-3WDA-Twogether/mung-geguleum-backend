package com.capstone.domain.heart.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class HeartNotFoundException extends CustomException {
    public HeartNotFoundException() {
        super(ErrorCode.HEART_NOT_FOUND_ERROR);
    }
}
