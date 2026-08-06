package com.vasi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vasi.security.JwtFilter;

@Configuration

public class SecurityConfig {
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter=jwtFilter;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers(
	        			    "/api/auth/login",
	        			    "/api/auth/signup",
	        			    "/",
	        			    "/index.html",
	        			    "/login.html",
	        			    "/signup.html",
	        			    "/courses.html",
	        			    "/attendance.html",
	        			    "/grades.html",
	        			    "/dashboard.html",
	        			    "/style.css",
	        			    "/auth.css",
	        			    "/script.js",
	        			    "/auth.js",
	        			    "/courses.js",
	        			    "/attendance.js",
	        			    "/grades.js",
	        			    "/dashboard.js",
	        			    "/favicon.ico",
	        			    "/favicon.svg"
	        			).permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
}
