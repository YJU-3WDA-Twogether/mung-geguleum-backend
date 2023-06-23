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
			
			.requestMatchers("/s3/url/**").permitAll()
			.requestMatchers("/token" ).permitAll()
			.requestMatchers("/user/login", "/user/read/**","/user/create" ,"/user/useridchk/**","/user/emailchk/**","/user/nicknamechk/**" ).permitAll() //user관련된 permitall입니다.
			.requestMatchers("/post/getlist/**","/post/read/**" , "/post/getMyPost/**", "/post/getSearchPost/**" ).permitAll() //post컨트롤러와 관련된 설정
			.requestMatchers("/file/read/**").permitAll()
			.requestMatchers("/log/getlist").permitAll()
			.requestMatchers("/postSource/getlist/**").permitAll()
			//.requestMatchers("/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/user/logout","/user/update/**","/user/delete/**").hasAnyAuthority("USER")
			.requestMatchers("/post/create" , "/post/update/**" , "/post/delete/**"  ).hasAnyAuthority("USER")
			.requestMatchers("/file/delete/**","file/download/**").hasAnyAuthority("USER")
			.requestMatchers("/log/getpostsourcelist","/log/getdownlist" ,"/log/report/**").hasAnyAuthority("USER")
			.requestMatchers("/heart/**").hasAnyAuthority("USER")
			.requestMatchers("/reply/**").hasAnyAuthority("USER")
			
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

