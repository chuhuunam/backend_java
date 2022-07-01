package com.example.backend_java.service;

import com.example.backend_java.domain.request.LoaiHopDongRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LoaiHopDongService {
    ResponseEntity<?> getAll();

    ResponseEntity<?> createTypeContract(HttpServletRequest request, LoaiHopDongRequest req);

    ResponseEntity<?> updateTypeContract(HttpServletRequest request, LoaiHopDongRequest req, Long id);

    ResponseEntity<?> deleteTypeContract(Long id);

    ResponseEntity<?> getPage(Integer index, Integer size, String tenHopDong, String loaiHopDong, Integer baoHiem);
}
