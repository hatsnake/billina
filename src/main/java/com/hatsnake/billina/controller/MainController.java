package com.hatsnake.billina.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hatsnake.billina.domain.SessionUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
	private final HttpSession httpSession;
	
	@GetMapping("/")
	public String main(Model model) {		
		log.info("Main Page Start.");
		
		return "main";
	}
}
