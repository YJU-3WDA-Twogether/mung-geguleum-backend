package com.capstone.global.logger;

import java.util.Enumeration;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	    log.debug("=========================================================");
	    log.debug("==================== PreHandle Start ==================== \n");
	    long currentTime = System.currentTimeMillis();
	    request.setAttribute("bTime", currentTime);
	    
	    // 사용자가 보낸 파라미터 조회
		log.debug("============{}",request.getHeaderNames());
		Enumeration<String> HeaderNames = request.getHeaderNames();
		while (HeaderNames.hasMoreElements()) {
			String HeaderName = HeaderNames.nextElement();
			String HeaderValue = request.getParameter(HeaderName);
			log.debug("Parameter: {} = {} \n", HeaderName, HeaderValue);
		}
	    Enumeration<String> parameterNames = request.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
	        String parameterName = parameterNames.nextElement();
	        String parameterValue = request.getParameter(parameterName);
	        log.debug("Parameter: {} = {} \n", parameterName, parameterValue);
	    }


	    // 요청 방식과 주소 로깅
	    String method = request.getMethod();
	    String uri = request.getRequestURI();
	    log.debug("Request Method: {}", method);
	    log.debug("Request URI: {}", uri);
	    log.debug("==================== PreHandle End =====================");
	    log.debug("======================================================== \n");
	    return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){	    
	}



	  @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	        // 응답 데이터 로깅
		  //System.out.println("afterCompletion");
		    log.debug("===============================================================");
		    log.debug("==================== afterCompletion Start ==================== \n");
		    
		    long currentTime = System.currentTimeMillis();
		    long beginTime = (long)request.getAttribute("bTime");
		    long processedTime = currentTime - beginTime;		
		    log.debug("총 요청 시간은 {}",processedTime);
		    
		    log.debug("==================== afterCompletion End ====================");
		    log.debug("============================================================== \n");
		
	    }

}