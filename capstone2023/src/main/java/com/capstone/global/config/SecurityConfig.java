package com.capstone.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capstone.global.security.jwt.filter.ExceptionHandlerFilter;
import com.capstone.global.security.jwt.filter.JwtAuthenticationEntryPoint;
import com.capstone.global.security.jwt.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
 
    
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final ExceptionHandlerFilter exceptionHandlerFilter;

    
    @Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		return http
			.csrf().disable()
			.cors()
			.and()
			.authorizeHttpRequests()
			.requestMatchers("/**").permitAll()
			.requestMatchers("/token", "/user/login","/user/create","/user/read/**","post/getlist/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
			.build();
	}
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

