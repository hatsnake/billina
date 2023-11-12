package com.hatsnake.billina.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hatsnake.billina.common.security.CustomLoginFailureHandler;
import com.hatsnake.billina.common.security.CustomLoginSuccessHandler;
import com.hatsnake.billina.common.security.CustomOAuth2UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	
	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	@Bean 
	AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomLoginFailureHandler();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true);
		
		http
			.csrf().disable()
			.formLogin()
			.loginPage("/login").disable()
			.httpBasic().disable()
			.oauth2Login()
			.loginPage("/login")
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
			.userInfoEndpoint()
			.userService(customOAuth2UserService)
			;
		
		return http.build();
	}
}
