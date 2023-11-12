package com.hatsnake.billina.common.security;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hatsnake.billina.domain.SessionUser;
import com.hatsnake.billina.domain.Users;
import com.hatsnake.billina.repository.UserMapper;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserMapper userMapper;
	private final HttpSession httpSession;
	
	public CustomOAuth2UserService(UserMapper userMapper, HttpSession httpSession) {
		this.userMapper = userMapper;
		this.httpSession = httpSession;
	}
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		
		Users user = saveOrUpdate(attributes);
		httpSession.setAttribute("user", new SessionUser(user));
		System.out.println("user info - " + user.toString());
		System.out.println("attributes email info - " + attributes.getEmail());
		System.out.println("attributes name info - " + attributes.getName());
		System.out.println("attributes picture info - " + attributes.getPicture());
		System.out.println("attributes provider info - " + attributes.getProvider());
		
		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey()
		);
	}
	
	private Users saveOrUpdate(OAuthAttributes attributes) {
		Users user = null;
		try {
			user = userMapper.findUserByEmail(attributes.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Users newUser = new Users();
		newUser.setEmail(attributes.getEmail());
		newUser.setName(attributes.getName());
		newUser.setPicture(attributes.getPicture());
		newUser.setProvider(attributes.getProvider());
		
		if(user == null) {
			try {
				userMapper.insertUser(newUser);
				user = userMapper.findUserByEmail(newUser.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				userMapper.updateUser(newUser);
				user = userMapper.findUserByEmail(newUser.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}

}
