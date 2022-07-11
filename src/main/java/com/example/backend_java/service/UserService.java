package com.example.backend_java.service;

import com.example.backend_java.domain.request.PasswordRequest;
import com.example.backend_java.domain.request.StatusRequest;
import com.example.backend_java.domain.request.UserRequest;
import com.example.backend_java.domain.request.updateDepRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface UserService {
    ResponseEntity<?> getPageUser(HttpServletRequest request, String keyword, Integer idPhongBan, Integer idChucVu, Integer index, Integer size);

    ResponseEntity<?> createUser(HttpServletRequest request, UserRequest user) throws MessagingException;

    ResponseEntity<?> updateUser(HttpServletRequest request, UserRequest user, Long id);

    ResponseEntity<?> deleteUser(Long id);

    ResponseEntity<?> updatePassword(HttpServletRequest request, PasswordRequest password, Long id);

    ResponseEntity<?> updateStatus(HttpServletRequest request, StatusRequest status, Long id);

    ResponseEntity<?> updateDepartment(HttpServletRequest request, updateDepRequest department, Long id);

    void exportFile(HttpServletResponse response) throws IOException;

    ResponseEntity<?> getUser(Long id);
}
