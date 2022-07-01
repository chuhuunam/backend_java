package com.example.backend_java.service;

import com.example.backend_java.domain.request.LyDoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LyDoService {
    ResponseEntity<?> listCha(Integer id_cha);

    ResponseEntity<?> createLyDo(HttpServletRequest request, LyDoRequest reason);

    ResponseEntity<?> updateLyDo(HttpServletRequest request, LyDoRequest reason, Long id);

    ResponseEntity<?> deleteLyDo(Long id);

    ResponseEntity<?> getPage(Integer index, Integer size, String lyDo, Integer idCha);
}
