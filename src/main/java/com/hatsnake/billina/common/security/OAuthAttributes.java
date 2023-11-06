package com.hatsnake.billina.common.security;

import java.util.Map;

import com.hatsnake.billina.domain.Role;
import com.hatsnake.billina.domain.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	private String provider;
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		if("naver".equals(registrationId)) {
			return ofNaver("id", attributes);
		}
		
		return ofGoogle(userNameAttributeName, attributes);
	}
	
	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.picture((String) attributes.get("picture"))
				.provider("GOOGLE")
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		
		return OAuthAttributes.builder()
				.name((String) response.get("name"))
				.email((String) response.get("email"))
				.picture((String) response.get("profile_image"))
				.provider("NAVER")
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	public Users toEntity() {
		return Users.builder()
				.name(name)
				.email(email)
				.role(Role.USER)
				.build();
	}
}
