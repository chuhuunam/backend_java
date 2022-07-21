package com.example.backend_java.domain.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
	private String hoTen;
	private String token;
	private Long id;
	private String taiKhoan;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id,String hoTen, String taiKhoan, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.hoTen = hoTen;
		this.taiKhoan = taiKhoan;
		this.email = email;
		this.roles = roles;
	}
}
