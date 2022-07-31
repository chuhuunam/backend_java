package com.example.backend_java.service;

import com.example.backend_java.domain.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface UserService {
    ResponseEntity<?> getPageUser(HttpServletRequest request, String keyword, Integer idPhongBan, Integer idChucVu, Integer idLoaiHopDong, String sex, Integer index, Integer size);

    ResponseEntity<?> createUser(HttpServletRequest request, UserRequest user) throws MessagingException, IOException;

    ResponseEntity<?> updateUser(HttpServletRequest request, UserRequest user, Long id) throws IOException;

    ResponseEntity<?> deleteUser(Long id);

    ResponseEntity<?> updatePassword(HttpServletRequest request, PasswordRequest password, Long id);

    ResponseEntity<?> updateDepartment(HttpServletRequest request, updateDepRequest department, Long id);

    void exportFile(HttpServletResponse response) throws IOException;

    ResponseEntity<?> getUser(Long id);

    ResponseEntity<?> getList(HttpServletRequest request);

    ResponseEntity<?> statistical();

    ResponseEntity<?> quit_job(HttpServletRequest request, NghiViecRequest nghiViecRequest, Long id);

    ResponseEntity<?> getUserLeave(String keyword, Integer id_department, Integer id_position, Integer index, Integer size);

//    void s(HttpServletRequest request) throws NoSuchAlgorithmException;
}
