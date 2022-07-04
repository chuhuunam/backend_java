package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.HopDongDTO;
import com.example.backend_java.domain.entity.ChucVuEntity;
import com.example.backend_java.domain.entity.HopDongEntity;
import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.HopDongRequest;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.ChucVuRepository;
import com.example.backend_java.repository.HopDongRepository;
import com.example.backend_java.repository.LoaiHopDongRepository;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.HopDongService;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class HopDongServiceImpl implements HopDongService {

    private final HopDongRepository hopDongRepository;
    private final LoaiHopDongRepository loaiHopDongRepository;
    private final UserRepository userRepository;
    private final ChucVuRepository chucVuRepository;
    private final JwtUtils jwtUtils;

    public HopDongServiceImpl(HopDongRepository hopDongRepository, LoaiHopDongRepository loaiHopDongRepository, UserRepository userRepository, ChucVuRepository chucVuRepository, JwtUtils jwtUtils) {
        this.hopDongRepository = hopDongRepository;
        this.loaiHopDongRepository = loaiHopDongRepository;
        this.userRepository = userRepository;
        this.chucVuRepository = chucVuRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size) {
        Integer offset = (index - 1) * size;
        List<Object[]> page = hopDongRepository.getHopDong(offset,size);
        ArrayList<HopDongDTO> list = new ArrayList<>();
        for (Object[] entity : page) {
            list.add(new HopDongDTO(entity[0], entity[1],entity[2], entity[3],entity[4],entity[5],entity[6], entity[7],(Float)entity[8], entity[9],(Integer) entity[10],entity[11]));
        }
        PageResponse<HopDongEntity> data = new PageResponse(index, size, (long) page.size(), list);
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
    }

    @Override
    public ResponseEntity<?> createHopDong(HttpServletRequest request, HopDongRequest hopdong) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            HopDongEntity entity = new HopDongEntity();
            Integer stt = hopDongRepository.getId();
            if (stt == null){
                stt =1;
            }else {
                stt = stt+1;
            }
            LoaiHopDongEntity loaihopdong = loaiHopDongRepository.findById(Long.valueOf(hopdong.getId_loai_hop_dong())).get();
            entity.setLoaihopdong(loaihopdong);
            UserEntity user = userRepository.findById(Long.valueOf(hopdong.getId_user())).get();
            entity.setNguoidung(user);
            ChucVuEntity chucVu = chucVuRepository.findById(Long.valueOf(hopdong.getId_chuc_vu())).get();
            entity.setPositions(chucVu);
            entity.setMaHopDong("HD_"+stt);
            entity.setNgayKy(hopdong.getNgayKy());
            entity.setNgayHieuLuc(hopdong.getNgayHieuLuc());
            entity.setNgayKetThuc(hopdong.getNgayKetThuc());
            entity.setLuong(hopdong.getLuong());
            entity.setStatus(hopdong.getStatus());
            entity.setMoTa(hopdong.getMoTa());
            entity.setNguoiTao(userEntity.getHoTen());
            hopDongRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> updateHopDong(HttpServletRequest request, HopDongRequest req, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            HopDongEntity entity = hopDongRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            LoaiHopDongEntity loaihopdong = loaiHopDongRepository.findById(Long.valueOf(req.getId_loai_hop_dong())).get();
            entity.setLoaihopdong(loaihopdong);
            UserEntity user = userRepository.findById(Long.valueOf(req.getId_user())).get();
            entity.setNguoidung(user);
            ChucVuEntity chucVu = chucVuRepository.findById(Long.valueOf(req.getId_chuc_vu())).get();
            entity.setPositions(chucVu);
            entity.setNgayKy(req.getNgayKy());
            entity.setNgayHieuLuc(req.getNgayHieuLuc());
            entity.setNgayKetThuc(req.getNgayKetThuc());
            entity.setLuong(req.getLuong());
            entity.setStatus(req.getStatus());
            entity.setMoTa(req.getMoTa());
            entity.setNguoiSua(userEntity.getHoTen());
            hopDongRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> deleteHopDong(Long id) {
        try {
            HopDongEntity entity = hopDongRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            hopDongRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }
}
