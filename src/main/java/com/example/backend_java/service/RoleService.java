package com.example.backend_java.service;

import com.example.backend_java.domain.request.RoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RoleService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> createRole(HttpServletRequest request, RoleRequest role);

    ResponseEntity<?> updateRole(HttpServletRequest request, RoleRequest role, Long id);

    ResponseEntity<?> deleteRole(Long id);
}
