package com.capstone.domain.reply.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class ReplyNullPointException extends CustomException {

    public ReplyNullPointException() {   //예외처리 임의의 예외처리 사용중 (수정해야함)
        super(ErrorCode.REPLY_NULL_POINT_ERROR);
    }
}
