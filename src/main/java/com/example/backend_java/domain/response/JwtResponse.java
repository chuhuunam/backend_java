package com.example.backend_java.domain.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
	private String fullName;
	private String token;
	private Long id;
	private String userName;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id,String fullName, String userName, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.email = email;
		this.roles = roles;
	}
}
