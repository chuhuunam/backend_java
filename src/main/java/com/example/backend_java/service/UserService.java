package com.example.backend_java.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {
    ResponseEntity<?> getPageUser(HttpServletRequest request, Integer index, Integer size);
}
