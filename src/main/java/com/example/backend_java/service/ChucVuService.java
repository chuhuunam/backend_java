package com.example.backend_java.service;

import com.example.backend_java.domain.request.ChucVuRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ChucVuService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> createChucVu(HttpServletRequest request, ChucVuRequest position);

    ResponseEntity<?> updateChucVu(HttpServletRequest request, ChucVuRequest position, Long id);

    ResponseEntity<?> deleteChucVu(Long id);

    ResponseEntity<?> getPage(Integer pageIndex, Integer pageSize, String keyword);
}
