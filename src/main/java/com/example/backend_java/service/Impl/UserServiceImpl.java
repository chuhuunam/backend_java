package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.UserDto;
import com.example.backend_java.domain.entity.*;
import com.example.backend_java.domain.request.PasswordRequest;
import com.example.backend_java.domain.request.StatusRequest;
import com.example.backend_java.domain.request.UserRequest;
import com.example.backend_java.domain.request.updateDepRequest;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.*;
import com.example.backend_java.service.UserService;
import com.example.backend_java.utils.DataUtils;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder _passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final LoaiHopDongRepository loaiHopDongRepository;
    private final PhongBanRepository phongBanRepository;
    private final ChucVuRepository chucVuRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(JwtUtils jwtUtils, UserRepository userRepository, LoaiHopDongRepository loaiHopDongRepository, PhongBanRepository phongBanRepository, ChucVuRepository chucVuRepository, RoleRepository roleRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.loaiHopDongRepository = loaiHopDongRepository;
        this.phongBanRepository = phongBanRepository;
        this.chucVuRepository = chucVuRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> getPageUser(HttpServletRequest request, String keyword, Integer idPhongBan, Integer idChucVu, Integer index, Integer size) {
        UserEntity userEntity = jwtUtils.getUserEntity(request);
        Set<RoleEntity> en =userEntity.getRoles();
        Integer offset = (index - 1) * size;
        for (RoleEntity s : en) {
            List<Object[]> page;
            if (s.getMaQuyen().equals("Admin")){
                page = userRepository.getUser(keyword,idPhongBan,idChucVu,offset, size);
                ArrayList<UserDto> list = new ArrayList<>();
                for (Object[] entity : page) {
                    LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[14]);
                    list.add(new UserDto(entity[0],entity[1],entity[2],entity[3],entity[4],entity[5],entity[6],
                            entity[7],entity[8],entity[9],(Boolean)entity[10],entity[11],entity[12],entity[13],loaihd.getTenHopDong(),
                            loaihd.getBaoHiem(),entity[15],entity[16]));
                }
                PageResponse<HopDongEntity> data = new PageResponse(index, size, (long) page.size(), list);
                return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
            }else {
                Integer depart = Math.toIntExact(userEntity.getDepartments().getId());
                page = userRepository.getUser(keyword,depart,idChucVu,offset, size);
                ArrayList<UserDto> list = new ArrayList<>();
                for (Object[] entity : page) {
                    LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[14]);
                    list.add(new UserDto(entity[0],entity[1],entity[2],entity[3],entity[4],entity[5],entity[6],
                            entity[7],entity[8],entity[9],(Boolean)entity[10],entity[11],entity[12],entity[13],loaihd.getTenHopDong(),
                            loaihd.getBaoHiem(),entity[15],entity[16]));
                }
                PageResponse<HopDongEntity> data = new PageResponse(index, size, (long) page.size(), list);
                return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<?> createUser(HttpServletRequest request, UserRequest user) {
        try {
            if (userRepository.existsAllByEmail(user.getEmail())) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Error: Email is already in use!"));
            }
            String[] tmp = user.getHoTen().split(" ");
            String username = "";
            for (int i = tmp.length - 1; i >= 0; i--) {
                if (i == tmp.length - 1) {
                    username = username + tmp[i].toLowerCase();
                }
            }
            for (int j = 0; j < tmp.length - 1; j++) {
                username = username + tmp[j].toLowerCase().charAt(0);
            }
            Integer count = userRepository.Count(username);

            String pass = DataUtils.generateTempPwd(6);
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            UserEntity entity = new UserEntity();
            entity.setEmail(user.getEmail());
            entity.setHoTen(user.getHoTen());
            if(count>0){
                entity.setTaiKhoan(username+count);
            }else {
                entity.setTaiKhoan(username);
            }
            entity.setSoDienThoai(user.getSoDienThoai());
            entity.setAnhDaiDien(user.getAnhDaiDien());
            entity.setCmt(user.getCmt());
            entity.setMatKhau(_passwordEncoder.encode(pass));
            entity.setDiaChi(user.getDiaChi());
            entity.setGioiTinh(user.getGioiTinh());
            entity.setNgaySinh(user.getNgaySinh());
            entity.setStatus(true);
            PhongBanEntity department = phongBanRepository.findById(Long.valueOf(user.getId_phong_ban())).get();
            entity.setDepartments(department);
            ChucVuEntity position = chucVuRepository.findById(Long.valueOf(user.getId_chuc_vu())).get();
            entity.setPositions(position);
            Set<String> strRoles = user.getRoles();
            Set<RoleEntity> roles = new HashSet<>();
            if (strRoles == null) {
                RoleEntity userRole = roleRepository.findByMaQuyen("User");
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    RoleEntity adminRole = roleRepository.findByMaQuyen(role);
                    if (adminRole == null) {
                        RoleEntity RoleUser = roleRepository.findByMaQuyen("User");
                        roles.add(RoleUser);
                    }
                    roles.add(adminRole);
                });
            }
            entity.setRoles(roles);
            entity.setNguoiTao(userEntity.getHoTen());
            userRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }
    @Override
    public ResponseEntity<?> updateUser(HttpServletRequest request, UserRequest user, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            UserEntity entity = userRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setEmail(user.getEmail());
            entity.setSoDienThoai(user.getSoDienThoai());
            entity.setAnhDaiDien(user.getAnhDaiDien());
            entity.setCmt(user.getCmt());
            entity.setDiaChi(user.getDiaChi());
            entity.setGioiTinh(user.getGioiTinh());
            entity.setNgaySinh(user.getNgaySinh());
            entity.setStatus(user.isStatus());
            PhongBanEntity department = phongBanRepository.findById(Long.valueOf(user.getId_phong_ban())).get();
            entity.setDepartments(department);
            ChucVuEntity position = chucVuRepository.findById(Long.valueOf(user.getId_chuc_vu())).get();
            entity.setPositions(position);
            Set<String> strRoles = user.getRoles();
            Set<RoleEntity> roles = new HashSet<>();
            if (strRoles == null) {
                RoleEntity userRole = roleRepository.findByMaQuyen("User");
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    RoleEntity adminRole = roleRepository.findByMaQuyen(role);
                    if (adminRole == null) {
                        RoleEntity RoleUser = roleRepository.findByMaQuyen("User");
                        roles.add(RoleUser);
                    }
                    roles.add(adminRole);
                });
            }
            entity.setRoles(roles);
            entity.setNguoiSua(userEntity.getHoTen());
            userRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        try {
            UserEntity entity = userRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            userRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "System busy"));
        }
    }
    @Override
    public ResponseEntity<?> updatePassword(HttpServletRequest request, PasswordRequest password, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            UserEntity entity = userRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setMatKhau(_passwordEncoder.encode(password.getMat_khau()));
            entity.setNguoiSua(userEntity.getHoTen());
            userRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, e));
        }
    }

    @Override
    public ResponseEntity<?> updateStatus(HttpServletRequest request, StatusRequest status, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            UserEntity entity = userRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            entity.setStatus(status.isStatus());
            entity.setNguoiSua(userEntity.getHoTen());
            userRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, e));
        }
    }

    @Override
    public ResponseEntity<?> updateDepartment(HttpServletRequest request, updateDepRequest department, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            UserEntity entity = userRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            PhongBanEntity dep = phongBanRepository.findById(Long.valueOf(department.getId_phong_ban())).get();
            entity.setDepartments(dep);
            entity.setNguoiSua(userEntity.getHoTen());
            userRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, e));
        }
    }
}
