package com.example.backend_java.service;

import com.example.backend_java.domain.request.HopDongRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface HopDongService {
    ResponseEntity<?> getPage(Integer index, Integer size);

    ResponseEntity<?> createHopDong(HttpServletRequest request, HopDongRequest hopdong);

    ResponseEntity<?> updateHopDong(HttpServletRequest request, HopDongRequest req, Long id);

    ResponseEntity<?> deleteHopDong(Long id);
}
