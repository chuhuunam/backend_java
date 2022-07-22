package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.RoleDto;
import com.example.backend_java.domain.entity.RoleEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.RoleRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.RoleRepository;
import com.example.backend_java.service.RoleService;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    public RoleServiceImpl(RoleRepository roleRepository, JwtUtils jwtUtils) {
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<RoleEntity> list = roleRepository.findAll();
            ArrayList<RoleDto> getAll = new ArrayList<>();
            for (RoleEntity entity : list) {
                RoleDto r = new RoleDto();
                r.fromEntity(entity);
                getAll.add(r);
            }
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Danh sách quyền lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> createRole(HttpServletRequest request, RoleRequest role) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            RoleEntity entity = new RoleEntity();
            entity.setMaQuyen(role.getMaQuyen());
            entity.setTenQuyen(role.getTenQuyen());
            entity.setMoTa(role.getMoTa());
            entity.setStatus(true);
            entity.setNguoiTao(userEntity.getHoTen());
            roleRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm quyền thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Thêm quyền lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> updateRole(HttpServletRequest request, RoleRequest role, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            RoleEntity entity = roleRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setMaQuyen(role.getMaQuyen());
            entity.setTenQuyen(role.getTenQuyen());
            entity.setMoTa(role.getMoTa());
            entity.setStatus(role.isStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            roleRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa quyền thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Sửa quyền lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> deleteRole(Long id) {
        try {
            RoleEntity entity = roleRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            roleRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa quyền thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Xóa quyền lỗi"));
        }
    }
}
