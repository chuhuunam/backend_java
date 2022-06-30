package com.example.backend_java.service;

import com.example.backend_java.domain.request.PhongBanRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PhongBanService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> createDepartment(HttpServletRequest httpServletRequest, PhongBanRequest request);

    ResponseEntity<?> updateDepartment(HttpServletRequest request, PhongBanRequest department, Long id);

    ResponseEntity<?> deleteDepartment(Long id);

    ResponseEntity<?> getPage(Integer pageIndex, Integer pageSize, String name);
}
