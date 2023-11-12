package com.hatsnake.billina.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private long id;
	private String name, email, role, picture, provider;
	
	public SessionUser(Users user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.role = user.getRoleKey();
		this.picture = user.getPicture();
	}
}
