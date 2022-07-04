package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.LyDoDto;
import com.example.backend_java.domain.entity.LyDoEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.LyDoRequest;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.LyDoRepository;
import com.example.backend_java.service.LyDoService;
import com.example.backend_java.utils.JwtUtils;
import com.example.backend_java.utils.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LyDoServiceImpl implements LyDoService {

    private final LyDoRepository lyDoRepository;
    private final JwtUtils jwtUtils;

    public LyDoServiceImpl(LyDoRepository lyDoRepository, JwtUtils jwtUtils) {
        this.lyDoRepository = lyDoRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> listCha(Integer id_cha) {
        List<LyDoEntity> list = lyDoRepository.findById_cha(id_cha);
        ArrayList<LyDoDto> getAll = new ArrayList<>();
        for (LyDoEntity entity : list) {
            LyDoDto r = new LyDoDto();
            r.fromEntity(entity);
            getAll.add(r);
        }
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, getAll));
    }

    @Override
    public ResponseEntity<?> getPage(Integer index, Integer size, String lyDo, Integer idCha) {
        Pageable pageable = PageRequest.of(index - 1, size, Sort.by("id").descending());
//        Page<LyDoEntity> page;
//        if (lyDo != null){
        Page<LyDoEntity> page = lyDoRepository.search(lyDo,idCha,pageable);
//        }
//        else {
//            page = lyDoRepository.findAll(pageable);
//        }
        ArrayList<LyDoDto> list = new ArrayList<LyDoDto>();
        for (LyDoEntity entity : page.getContent()) {
            String LyDoCha = null;
            if(entity.getId_cha() != 0){
                Optional<LyDoEntity> lydo = lyDoRepository.findById(Long.valueOf(entity.getId_cha()));
                LyDoCha = lydo.get().getLyDo();
            }
            list.add(new LyDoDto(entity.getId(),entity.getLyDo(),LyDoCha,entity.getHuongLuong(),entity.getStatus(),
                    entity.getNguoiTao(),entity.getNguoiSua(), TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao()),TimeUtil.toHHmmDDMMyyyy((entity.getNgaySua()))));
        }
        PageResponse<LyDoDto> data = new PageResponse(index, size, page.getTotalElements(),list);
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
    }


    @Override
    public ResponseEntity<?> createLyDo(HttpServletRequest request, LyDoRequest reason) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            LyDoEntity entity = new LyDoEntity();
            entity.setId_cha(reason.getId_cha());
            entity.setLyDo(reason.getLyDo());
            entity.setHuongLuong(reason.getHuongLuong());
            entity.setNguoiTao(userEntity.getHoTen());
            lyDoRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));

        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> updateLyDo(HttpServletRequest request, LyDoRequest reason, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            LyDoEntity entity = lyDoRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setId_cha(reason.getId_cha());
            entity.setLyDo(reason.getLyDo());
            entity.setHuongLuong(reason.getHuongLuong());
            entity.setNguoiSua(userEntity.getHoTen());
            lyDoRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> deleteLyDo(Long id) {
        try {
            LyDoEntity entity = lyDoRepository.findById(id).orElse(null) ;
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            lyDoRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

}