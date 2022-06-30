package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.PhongBanDto;
import com.example.backend_java.domain.entity.PhongBanEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.PhongBanRequest;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.PhongBanRepository;
import com.example.backend_java.service.PhongBanService;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhongBanServiceImpl implements PhongBanService {

    private final PhongBanRepository phongBanRepository;
    private final JwtUtils jwtUtils;

    public PhongBanServiceImpl(PhongBanRepository phongBanRepository, JwtUtils jwtUtils) {
        this.phongBanRepository = phongBanRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public ResponseEntity<?> getAll() {
        List<PhongBanEntity> list = phongBanRepository.findAll();
        ArrayList<PhongBanDto> getAll = new ArrayList<>();
        for (PhongBanEntity entity : list) {
            PhongBanDto r = new PhongBanDto();
            r.fromEntity(entity);
            getAll.add(r);
        }
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
    }

    @Override
    public ResponseEntity<?> createDepartment(HttpServletRequest httpServletRequest, PhongBanRequest request) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(httpServletRequest);
            PhongBanEntity entity = new PhongBanEntity();
            entity.setMaPhongBan(request.getMaPhongBan());
            entity.setTenPhongBan(request.getTenPhongBan());
            entity.setMoTa(request.getMoTa());
            entity.setStatus(request.getStatus());
            entity.setNguoiTao(userEntity.getHoTen());
            phongBanRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));

        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> updateDepartment(HttpServletRequest request, PhongBanRequest department, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            PhongBanEntity entity = phongBanRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setMaPhongBan(department.getMaPhongBan());
            entity.setTenPhongBan(department.getTenPhongBan());
            entity.setMoTa(department.getMoTa());
            entity.setStatus(department.getStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            phongBanRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Long id) {
        try {
            PhongBanEntity entity = phongBanRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            phongBanRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size, String name) {
        Page<PhongBanEntity> page;
        Pageable pageable = PageRequest.of(index - 1, size, Sort.by("id").descending());
        if (name == null){
            page = phongBanRepository.findAll(pageable);
        }
        else {
            page = phongBanRepository.findAll(name,pageable);
        }
        ArrayList<PhongBanDto> list = new ArrayList<>();
        for (PhongBanEntity entity : page.getContent()) {
            PhongBanDto r = new PhongBanDto();
            r.fromEntity(entity);
            list.add(r);
        }
        PageResponse<PhongBanDto> data = new PageResponse(index, size, page.getTotalElements(), list);

        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
    }
}
