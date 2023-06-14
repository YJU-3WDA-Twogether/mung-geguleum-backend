package com.capstone.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private HttpStatus status;
	private String code;
	private String message;


	private ErrorResponse(final ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
	}
	
	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}
    
  
}
