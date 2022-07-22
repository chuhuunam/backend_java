package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.ChucVuDto;
import com.example.backend_java.domain.entity.ChucVuEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.ChucVuRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.ChucVuRepository;
import com.example.backend_java.service.ChucVuService;
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
public class ChucVuServiceImpl implements ChucVuService {

    private final ChucVuRepository chucVuRepository;
    private final JwtUtils jwtUtils;

    public ChucVuServiceImpl(ChucVuRepository chucVuRepository, JwtUtils jwtUtils) {
        this.chucVuRepository = chucVuRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<ChucVuEntity> list = chucVuRepository.findAll();
            ArrayList<ChucVuDto> getAll = new ArrayList<>();
            for (ChucVuEntity entity : list) {
                ChucVuDto r = new ChucVuDto();
                r.fromEntity(entity);
                getAll.add(r);
            }
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Danh sách chức vụ lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> createChucVu(HttpServletRequest request, ChucVuRequest position) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            ChucVuEntity entity = new ChucVuEntity();
            entity.setMaChucVu(position.getMaChucVu());
            entity.setTenChucVu(position.getTenChucVu());
            entity.setMoTa(position.getMoTa());
            entity.setStatus(true);
            entity.setNguoiTao(userEntity.getHoTen());
            chucVuRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Thêm chức vụ lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> updateChucVu(HttpServletRequest request, ChucVuRequest position, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            ChucVuEntity entity = chucVuRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setMaChucVu(position.getMaChucVu());
            entity.setTenChucVu(position.getTenChucVu());
            entity.setMoTa(position.getMoTa());
            entity.setStatus(position.isStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            chucVuRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Sửa chức vụ lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> deleteChucVu(Long id) {
        try {
            ChucVuEntity entity = chucVuRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            chucVuRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Xóa chức vụ lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size, String keyword) {
        try {
            Pageable pageable = PageRequest.of(index - 1, size, Sort.by("id").descending());
            Page<ChucVuEntity> page;
            if (keyword == null) {
                page = chucVuRepository.findAll(pageable);
            } else {
                page = chucVuRepository.findAll(keyword, pageable);
            }
            ArrayList<ChucVuDto> list = new ArrayList<ChucVuDto>();
            for (ChucVuEntity entity : page.getContent()) {
                ChucVuDto r = new ChucVuDto();
                r.fromEntity(entity);
                list.add(r);
            }
            PageResponse<ChucVuDto> data = new PageResponse(index, size, page.getTotalElements(), list);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Phân trang chức vụ lỗi"));
        }
    }
}
