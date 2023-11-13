package com.hatsnake.billina.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hatsnake.billina.domain.SessionUser;
import com.hatsnake.billina.domain.Users;
import com.hatsnake.billina.repository.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	private final HttpSession httpSession;
	private final UserMapper userMapper;
	
	// 로그인 페이지
	@GetMapping("/login")
	public String login() {
		log.info("Login Page Start.");
		
		return "login";
	}
	
	// 유저 프로필 페이지
	@GetMapping("/user-profile")
	@PreAuthorize("isAuthenticated()")
	public String userProfile(Model model) {
		log.info("User Profile Page Start.");
		
		/*
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		model.addAttribute("userId", user.getId());
		*/
		
		return "users/user-profile";
	}
	
	// 유저 프로필 정보 가져오기
	@GetMapping("/users/{id}")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Users> getUserProfile(@PathVariable Long id) {
		log.info("getUserProfileInfo() Start.");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

		Users user = null;
		try {
			user = userMapper.findUserById(id);
			log.info("user : " + user.toString());
			
			if(user.getIntroduction() == null) {
				user.setIntroduction("자기소개가 없습니다.");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(sessionUser.getId() != user.getId()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	// 유저 자기소개 정보 수정
	@PutMapping("/users/{id}")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Users> updateUserIntro(@PathVariable Long id, @RequestBody Users user) {
		log.info("updateUserIntro() Start.");
		
		log.info("id : " + id);
		log.info("user : " + user.toString());
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

		Users existUser = null;
		try {
			existUser = userMapper.findUserById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(existUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(sessionUser.getId() != existUser.getId()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		existUser.setIntroduction(user.getIntroduction());
		
		try {
			userMapper.updateUserIntro(existUser);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return new ResponseEntity<>(existUser, HttpStatus.OK);
	}
}
