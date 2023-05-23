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
			+ "||execution(* com.capstone.domain.tag.controller..**(..)) || execution(* com.capstone.domain.tag.service..**(..))"
			+ "||execution(* com.capstone.domain.user.controller..**(..)) || execution(* com.capstone.domain.user.service..**(..))"
			+ "||execution(* com.capstone.domain.file.controller..**(..)) || execution(* com.capstone.domain.file.service..**(..))")
			//+ "||execution(* com.capstone.domain.logState.controller..**(..)) || execution(* com.capstone.domain.tag.service..**(..))"
			//+ "||execution(* com.capstone.domain.board.controller..**(..)) || execution(* com.capstone.domain.board.service..**(..))")
    private void cut(){}
	
	 // Pointcut에 의해 필터링된 경로로 들어오는 경우 메서드 호출 전에 적용
	//intercepter 부분에서 이미 똑같은 작업을 처리하므로 
//    @Before("cut()")
//    public void beforeParameterLog(JoinPoint joinPoint) {
//        // 메서드 정보 받아오기
//        Method method = getMethod(joinPoint);
//        log.info("===============================");
//        log.info("method name = {}", method.getName());
//
//        // 파라미터 받아오기
//        Object[] args = joinPoint.getArgs();
//        log.info("===============================");
//        if (args.length <= 0) log.info("no parameter");
//        for (Object arg : args) {
//            log.info("parameter type = {}", arg.getClass().getSimpleName());
//            log.info("parameter value = {} \n", arg);
//        }
//    }

    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
        // 메서드 정보 받아오기
        Method method = getMethod(joinPoint);
	    log.debug("========================================================");
	    log.debug("==================== Response Start ==================== \n");
	    
        log.info("method name = {}", method.getName());
        log.info("return type = {}", returnObj.getClass().getSimpleName());
        log.info("return value \n {} \n", returnObj);
        
        log.debug("===================== Response End =====================");
	    log.debug("========================================================\n");

       
    }

    // JoinPoint로 메서드 정보 가져오기
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

}
