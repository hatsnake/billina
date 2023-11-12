package com.hatsnake.billina.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {
	private Long id;
	private String name;
	private String email;
	private String password;
	private Role role;
	private String picture;
	private String provider;
	private String introduction;
	private String createdUser;
	private Date createdDate;
	private String updatedUser;
	private Date updatedDate;
	
	@Builder
	public Users(Long id, String name, String email, Role role, String picture, String provider, String createdUser) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.picture = picture;
		this.provider = provider;
	}
	
	public Users update(String name) {
		this.name = name;
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
}
