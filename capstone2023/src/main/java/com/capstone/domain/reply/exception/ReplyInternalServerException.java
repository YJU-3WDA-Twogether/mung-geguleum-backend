package com.capstone.domain.reply.exception;

import com.capstone.global.exception.CustomException;
import com.capstone.global.exception.ErrorCode;

public class ReplyInternalServerException extends CustomException {

    public ReplyInternalServerException() {super(ErrorCode.REPLY_INTERNAL_SERVER_ERROR);}

}
