package com.capstone.global.JWT.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capstone.global.JWT.exception.TokenException;
import com.capstone.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, e);
		}
	}

	private void setErrorResponse(HttpServletResponse response, TokenException tokenException) throws IOException {
		response.setStatus(tokenException.getErrorCode().getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
			.write(objectMapper.writeValueAsString(ErrorResponse.of(tokenException.getErrorCode())));
	}
}
