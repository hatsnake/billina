package com.hatsnake.billina.common.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMessage;
		
		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의해주세요.";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 계정입니다. 다시 확인해주세요.";
		} else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
		} else  {
			errorMessage = "알 수 없는 오류로 로그인 요청을 할 수 없습니다. 관리자에게 문의해주세요.";
		}
		
		errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
		setDefaultFailureUrl("/login?error=true&exception="+errorMessage);
		log.info("입력 아이디 : " + request.getParameter("username") + ", 로그인 실패 메시지 : " + errorMessage);
		
		super.onAuthenticationFailure(request, response, exception);
	}
	
	
	
}
