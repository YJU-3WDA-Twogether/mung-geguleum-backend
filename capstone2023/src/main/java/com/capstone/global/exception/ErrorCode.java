package com.capstone.global.exception;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	//Controller 단에서 처리할 에러코드
	 BAD_REQUEST_ERROR(400, "BadRequestError" , "잘못된 서버 요청입니다.")
	, MISSING_REQUEST_PARAMETER_ERROR(400,"MissingRequestParameterError","RequestBody 데이터가 존재하지 않습니다.") 
	, NVALID_PARAMS(400, "InvalidParams", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
	, NVALID_TYPE_VALUE(400, "Invalid Type Value", "유효하지 않은 데이터 타입 입니다.")  
	, UNAUTORIZED(401, "Unauthorized", "토큰 정보가 유효하지 않습니다.")
	, UNAVAILABLE(401, "Unavailable", "회원가입이 완료되지 않은 사용자입니다.")
	, FORBIDDEN_ERROR(403,"Forbidden Exception","권한이 없습니다.")
	, NULL_POINT_ERROR(404,"Null Point Exception","널값이 발생했습니다.")
	, NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다.")
	, NOT_VALID_ERROR(404, "G011", "handle Validation Exception") // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
	, CONFLICT(409, "Conflict", "데이터가 충돌되었습니다.")
	, INTERNAL_SERVER_ERROR(500 , "Internal Server Error" , "관리자에게 문의하여 주세요.")
	
	//Service 단에서 처리할 에러코드
	
	
	
	;
	
	private int status;
    private String code;
	private String message;
	
	public static ErrorCode valueOfCode(String errorCode) {
		return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
	}
	
}