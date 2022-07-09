package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.entity.RoleEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.UserService;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;

    public UserServiceImpl(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> getPageUser(HttpServletRequest request, Integer index, Integer size) {
        UserEntity userEntity = jwtUtils.getUserEntity(request);
        Set<RoleEntity> en =userEntity.getRoles();
        for (RoleEntity s : en) {
            System.out.println(s.getMaQuyen());
            if (s.getMaQuyen().equals("Admin")){

            }else {
            }
        }
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));
    }
}
