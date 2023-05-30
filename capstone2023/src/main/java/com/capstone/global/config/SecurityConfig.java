package com.capstone.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capstone.global.JWT.filter.ExceptionHandlerFilter;
import com.capstone.global.JWT.filter.JwtAuthenticationEntryPoint;
import com.capstone.global.JWT.filter.JwtAuthenticationFilter;

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
				.requestMatchers("/").permitAll()
//			.antMatchers("/token", "/api/**", "/login/**", "/oauth2/**").permitAll()
//			.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
//			.antMatchers("/api/v1/aws/s3").hasRole("USER")
			.anyRequest().authenticated()
			.and()
			.httpBasic().disable()	
			.rememberMe().disable()
			.formLogin().disable()
			.logout().disable()
			.requestCache().disable()
			.headers().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
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