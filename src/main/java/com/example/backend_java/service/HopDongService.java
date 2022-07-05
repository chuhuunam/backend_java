package com.example.backend_java.service;

import com.example.backend_java.domain.request.HopDongRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface HopDongService {
    ResponseEntity<?> getPage(String tenNhanVien, Integer maLoaiHopDong, Integer status, Integer index, Integer size);

    ResponseEntity<?> createHopDong(HttpServletRequest request, HopDongRequest hopdong);

    ResponseEntity<?> updateHopDong(HttpServletRequest request, HopDongRequest req, Long id);

    ResponseEntity<?> deleteHopDong(Long id);

    void UpdateTime();

    void exportFile(HttpServletResponse response) throws IOException;
}
