package com.hatsnake.billina.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Users {
	private Long id;
	private String name;
	private String email;
	private String password;
	private Role role;
	private String picture;
	private String provider;
	
	@Builder
	public Users(Long id, String name, String email, String password, Role role, String picture, String provider) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
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

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ "]";
	}
}
