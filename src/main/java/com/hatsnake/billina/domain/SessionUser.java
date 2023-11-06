package com.hatsnake.billina.domain;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String name, email, role, picture, provider;
	
	public SessionUser(Users user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRoleKey();
		this.picture = user.getPicture();
		this.provider = user.getProvider();
	}
}
