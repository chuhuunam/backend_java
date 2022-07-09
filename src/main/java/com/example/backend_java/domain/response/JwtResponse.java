package com.example.backend_java.domain.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
	private String fullname;
	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id,String fullname, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
}
