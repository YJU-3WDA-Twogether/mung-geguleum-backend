package com.capstone.global.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicateException(CustomException ex){
        log.info("handleEmailDuplicateException",ex);
        final ErrorResponse response = ErrorResponse.of(ex.getErrorCode());
        return new ResponseEntity<>(response, (ex.getErrorCode().getStatus()));
    }

   
    
	//바인딩 에러 - @RequestParam Enum Type 의심 (ex : Long 타입의 데이터를 전송해야하는데 String 타입으로 보낼 경우 에러발생)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		log.info("MethodArgumentTypeMismatchException : {}", ex.getMessage());
		final ErrorResponse response =  ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//입력 인자 수 부족 (ex: requestParam을 메소드의 메게변수로 요청했는데 안 넘겨줄 경우에 발생함)
	@ExceptionHandler(MissingRequestValueException.class) 
	public ResponseEntity<ErrorResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
		log.info("MissingRequestValueException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_PARAMS);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	//입력 인자 수 부족 (ex: requestPathVariable을 안넘겨주었을 경우에 발생함)
		@ExceptionHandler(MissingPathVariableException.class) 
		public ResponseEntity<ErrorResponse> handleMissingPathVariableException(MissingRequestValueException ex) {
			log.info("MissingRequestValueException : {}", ex.getMessage());
			final ErrorResponse response = ErrorResponse.of(ErrorCode.MISSING_REQUEST_VALUE_ERROR);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	//없는 API 요청 (ex: /post/read/10은 존재하지만 /post/aaa 형식으로 요청하면 발생하는 에러다.)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		
		log.info("NoHandlerFoundException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST_ERROR);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//잘못된 HttpMethod 요청 (ex : get방식요청인데 post로 요청할 경우)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		
		log.info("HttpRequestMethodNotSupportedException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	//권한 미보유
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		log.info("AccessDeniedException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.info("handleMethodArgumentNotValidException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST_ERROR);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//바인딩 에러가 나타냈을때 사용한다.
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> handleBindException(BindException ex) {
	    log.info("handleBindException : {}", ex.getMessage());
		final ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST_ERROR);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	//Exception 발생시에 처리한다.
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException(Exception ex){
	        log.error("handleException",ex);
	        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 
	 //IOException 발생시에 처리한다. 
	 @ExceptionHandler(IOException.class)
		public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
			log.info("IOException : {}", ex.getMessage());
			 final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
