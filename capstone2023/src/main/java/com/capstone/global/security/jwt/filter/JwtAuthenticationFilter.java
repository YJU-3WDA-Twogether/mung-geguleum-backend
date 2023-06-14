package com.capstone.global.security.jwt.filter;



import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capstone.global.security.jwt.JwtAuthenticationToken;
import com.capstone.global.security.jwt.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("======================================================== \n request : {}", request);
		String accessToken = tokenService.getAccessToken(request).orElse(null);
		if (accessToken != null) {
			JwtAuthenticationToken authenticationToken = tokenService.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			System.out.println(SecurityContextHolder.getContext().getAuthentication());
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public boolean shouldNotFilter(HttpServletRequest request) {
		return request.getRequestURI().equals("api/v1/token");

	}
}
