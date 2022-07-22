package com.example.backend_java.domain.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
	private String token;

	public JwtResponse(String accessToken) {
		this.token = accessToken;
	}
}
