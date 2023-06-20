package com.capstone.global.logger;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggerAop {
	
	//추후에 컨트롤러나 서비스가 늘어나면 주석처리한 부분처럼 늘리면됩니다.
	@Pointcut("execution(* com.capstone.domain.post.controller..**(..)) || execution(* com.capstone.domain.post.service..**(..))"
			+ "||execution(* com.capstone.domain.log.controller..**(..)) || execution(* com.capstone.domain.log.service..**(..))"
			+ "||execution(* com.capstone.domain.postSource.controller..**(..)) || execution(* com.capstone.domain.postSource.service..**(..))"
			+ "||execution(* com.capstone.domain.user.controller..**(..)) || execution(* com.capstone.domain.user.service..**(..))"
			+ "||execution(* com.capstone.domain.file.controller..**(..)) || execution(* com.capstone.domain.file.service..**(..))"
			//+ "||execution(* com.capstone.domain.admin.controller..**(..)) || execution(* com.capstone.domain.admin.service..**(..))"
		//	+ "||execution(* com.capstone.domain.hashtag.controller..**(..)) || execution(* com.capstone.domain.hashtag.service..**(..))"
		//	+ "||execution(* com.capstone.domain.posthashtag.controller..**(..)) || execution(* com.capstone.domain.posthashtag.service..**(..))"
			)
			//+ "||execution(* com.capstone.domain.logState.controller..**(..)) || execution(* com.capstone.domain.tag.service..**(..))"
			//+ "||execution(* com.capstone.domain.board.controller..**(..)) || execution(* com.capstone.domain.board.service..**(..))")
    private void cut(){}
	
    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
        // 메서드 정보 받아오기
        Method method = getMethod(joinPoint);
	    log.debug("========================================================");
	    log.debug("==================== Response Start ==================== \n");
	    
        log.info("method name = {}", method.getName());
        if(returnObj != null) {
	        log.info("return type = {}", returnObj.getClass().getSimpleName());
	        log.info("return value \n {} \n", returnObj);
        }else {
        	String str = "return 값이 없습니다.";
        	log.info("return type = {}", str);
	        log.info("return value \n {} \n", str);
        }
        log.debug("===================== Response End =====================");
	    log.debug("========================================================\n");
    }

    // JoinPoint로 메서드 정보 가져오기
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

}
