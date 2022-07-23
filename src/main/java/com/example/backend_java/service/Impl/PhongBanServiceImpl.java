package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.PhongBanDto;
import com.example.backend_java.domain.entity.PhongBanEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.PhongBanRequest;
import com.example.backend_java.domain.response.ErrResponse;
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
        try {
            List<PhongBanEntity> list = phongBanRepository.findAll();
            ArrayList<PhongBanDto> getAll = new ArrayList<>();
            for (PhongBanEntity entity : list) {
                PhongBanDto r = new PhongBanDto();
                r.fromEntity(entity);
                getAll.add(r);
            }
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Danh sách phòng ban lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> createDepartment(HttpServletRequest httpServletRequest, PhongBanRequest request) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(httpServletRequest);
            PhongBanEntity entity = new PhongBanEntity();
            entity.setMaPhongBan(request.getId_department());
            entity.setTenPhongBan(request.getName_department());
            entity.setMoTa(request.getDescribe());
            entity.setStatus(true);
            entity.setNguoiTao(userEntity.getHoTen());
            phongBanRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm phòng ban thành công"));

        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Thêm phòng ban lỗi"));

        }
    }

    @Override
    public ResponseEntity<?> updateDepartment(HttpServletRequest request, PhongBanRequest department, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            PhongBanEntity entity = phongBanRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setMaPhongBan(department.getId_department());
            entity.setTenPhongBan(department.getName_department());
            entity.setMoTa(department.getDescribe());
            entity.setStatus(department.isStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            phongBanRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa phòng ban thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Sửa phòng ban lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Long id) {
        try {
            PhongBanEntity entity = phongBanRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            phongBanRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa phòng ban thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Xóa phòng ban lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size, String keyword) {
        try {
            Page<PhongBanEntity> page;
            Pageable pageable = PageRequest.of(index - 1, size, Sort.by("id").descending());
            if (keyword == null) {
                page = phongBanRepository.findAll(pageable);
            } else {
                page = phongBanRepository.findAll(keyword, pageable);
            }
            ArrayList<PhongBanDto> list = new ArrayList<>();
            for (PhongBanEntity entity : page.getContent()) {
                PhongBanDto r = new PhongBanDto();
                r.fromEntity(entity);
                list.add(r);
            }
            PageResponse<PhongBanDto> data = new PageResponse(index, size, page.getTotalElements(), list);

            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Phân trang phòng ban lỗi"));
        }
    }
}
