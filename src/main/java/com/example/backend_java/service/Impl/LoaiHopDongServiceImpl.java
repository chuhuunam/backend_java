package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.LoaiHopDongDto;
import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.LoaiHopDongRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.LoaiHopDongRepository;
import com.example.backend_java.service.LoaiHopDongService;
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
public class LoaiHopDongServiceImpl implements LoaiHopDongService {

    private final LoaiHopDongRepository loaiHopDongRepository;
    private final JwtUtils jwtUtils;

    public LoaiHopDongServiceImpl(LoaiHopDongRepository loaiHopDongRepository, JwtUtils jwtUtils) {
        this.loaiHopDongRepository = loaiHopDongRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<LoaiHopDongEntity> list = loaiHopDongRepository.findAll();
            ArrayList<LoaiHopDongDto> getAll = new ArrayList<>();
            for (LoaiHopDongEntity entity : list) {
                LoaiHopDongDto r = new LoaiHopDongDto();
                r.fromEntity(entity);
                getAll.add(r);
            }
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Danh s??ch lo???i h???p ?????ng l???i"));
        }
    }

    @Override
    public ResponseEntity<?> createTypeContract(HttpServletRequest request, LoaiHopDongRequest req) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            LoaiHopDongEntity entity = new LoaiHopDongEntity();
            entity.setTenHopDong(req.getName_contract());
            entity.setBaoHiem(req.getInsurance());
            entity.setTinhChatLaoDong(req.getLabor_nature());
            entity.setStatus(true);
            entity.setNguoiTao(userEntity.getHoTen());
            loaiHopDongRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Th??m lo???i h???p ?????ng th??nh c??ng"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Th??m lo???i h???p ?????ng l???i"));
        }
    }

    @Override
    public ResponseEntity<?> updateTypeContract(HttpServletRequest request, LoaiHopDongRequest req, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            LoaiHopDongEntity entity = loaiHopDongRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Kh??ng th???y id"));
            }
            entity.setTenHopDong(req.getName_contract());
            entity.setBaoHiem(req.getInsurance());
            entity.setStatus(req.isStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            entity.setTinhChatLaoDong(req.getLabor_nature());
            loaiHopDongRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "S???a lo???i h???p ?????ng th??nh c??ng"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "S???a lo???i h???p ?????ng l???i"));

        }
    }

    @Override
    public ResponseEntity<?> deleteTypeContract(Long id) {
        try {
            LoaiHopDongEntity entity = loaiHopDongRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Kh??ng th???y id"));
            }
            loaiHopDongRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "X??a lo???i h???p ?????ng th??nh c??ng"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "X??a lo???i h???p ?????ng l???i"));

        }
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size, String tenHopDong, Integer baoHiem) {
        try {
            Page<LoaiHopDongEntity> page;
            Pageable pageable = PageRequest.of(index - 1, size, Sort.by("id").descending());
            page = loaiHopDongRepository.search(tenHopDong, baoHiem, pageable);
            ArrayList<LoaiHopDongDto> list = new ArrayList<>();
            for (LoaiHopDongEntity entity : page.getContent()) {
                LoaiHopDongDto r = new LoaiHopDongDto();
                r.fromEntity(entity);
                list.add(r);
            }
            PageResponse<LoaiHopDongDto> data = new PageResponse(index, size, page.getTotalElements(), list);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Ph??n trang lo???i h???p ?????ng l???i"));
        }
    }
}
