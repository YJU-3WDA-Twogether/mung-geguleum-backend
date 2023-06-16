package com.capstone.domain.posthashtag.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class HashtagNotFoundException extends CustomException {
    public HashtagNotFoundException() {
        super(ErrorCode.HASHTAG_NOT_FOUND_ERROR);
    }
}
