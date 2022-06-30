package com.example.backend_java.service.Impl;

import com.example.backend_java.domain.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String hoTen;

	private String taiKhoan;

	private String email;

	@JsonIgnore
	private String matKhau;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String taiKhoan,String hoTen, String email, String matKhau,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.hoTen = hoTen;
		this.taiKhoan = taiKhoan;
		this.email = email;
		this.matKhau = matKhau;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(UserEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getMaQuyen()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getTaiKhoan(),
				user.getHoTen(),
				user.getEmail(),
				user.getMatKhau(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return matKhau;
	}

	@Override
	public String getUsername() {
		return taiKhoan;
	}
	public Long getId() {
		return id;
	}

	public String getHoTen() {
		return hoTen;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
