package com.example.backend_java.service;

import com.example.backend_java.domain.request.LoginRequest;
import com.example.backend_java.domain.request.LogoutRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface AuthService {
    ResponseEntity<?> login(HttpServletResponse request, LoginRequest login);
}
