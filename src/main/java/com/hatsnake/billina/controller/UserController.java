package com.hatsnake.billina.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	@GetMapping("/login")
	public String login() {
		log.info("Login Page Start.");
		
		return "login";
	}
}
