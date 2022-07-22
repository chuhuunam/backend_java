package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.DataMailDto;
import com.example.backend_java.domain.dto.RoleDto;
import com.example.backend_java.domain.dto.UserDto;
import com.example.backend_java.domain.dto.UserLoginDto;
import com.example.backend_java.domain.entity.*;
import com.example.backend_java.domain.request.PasswordRequest;
import com.example.backend_java.domain.request.StatusRequest;
import com.example.backend_java.domain.request.UserRequest;
import com.example.backend_java.domain.request.updateDepRequest;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.*;
import com.example.backend_java.service.MailService;
import com.example.backend_java.service.UserService;
import com.example.backend_java.utils.DataUtils;
import com.example.backend_java.utils.JwtUtils;
import com.example.backend_java.utils.TimeUtil;
import com.example.backend_java.utils.VNCharacterUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private final MailService mailService;

    public UserServiceImpl(JwtUtils jwtUtils, UserRepository userRepository, LoaiHopDongRepository loaiHopDongRepository, PhongBanRepository phongBanRepository, ChucVuRepository chucVuRepository, RoleRepository roleRepository, MailService mailService) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.loaiHopDongRepository = loaiHopDongRepository;
        this.phongBanRepository = phongBanRepository;
        this.chucVuRepository = chucVuRepository;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<?> getPageUser(HttpServletRequest request, String keyword, Integer idPhongBan, Integer idChucVu, Integer index, Integer size) {
        UserEntity userEntity = jwtUtils.getUserEntity(request);
        Set<RoleEntity> en = userEntity.getRoles();
        Integer offset = (index - 1) * size;
        for (RoleEntity s : en) {
            List<Object[]> page;
            if (s.getMaQuyen().equals("Admin")) {
                page = userRepository.getUser(keyword, idPhongBan, idChucVu, offset, size);
                ArrayList<UserDto> list = new ArrayList<>();
                for (Object[] entity : page) {
                    LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[14]);
                    list.add(new UserDto(entity[0], entity[1], entity[2], entity[3], entity[4], entity[5], entity[6],
                            entity[7], entity[8], entity[9], (Boolean) entity[10], entity[11], entity[12], entity[13], loaihd.getTenHopDong(),
                            loaihd.getBaoHiem(), entity[15], entity[16]));
                }
                PageResponse<HopDongEntity> data = new PageResponse(index, size, (long) page.size(), list);
                return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
            } else {
                Integer depart = Math.toIntExact(userEntity.getDepartments().getId());
                page = userRepository.getUser(keyword, depart, idChucVu, offset, size);
                ArrayList<UserDto> list = new ArrayList<>();
                for (Object[] entity : page) {
                    LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[14]);
                    list.add(new UserDto(entity[0], entity[1], entity[2], entity[3], entity[4], entity[5], entity[6],
                            entity[7], entity[8], entity[9], (Boolean) entity[10], entity[11], entity[12], entity[13], loaihd.getTenHopDong(),
                            loaihd.getBaoHiem(), entity[15], entity[16]));
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
            DataMailDto dataMail = new DataMailDto();
            String[] tmp = user.getHoTen().split(" ");
            String username = "";
            for (int i = tmp.length - 1; i >= 0; i--) {
                if (i == tmp.length - 1) {
                    username = VNCharacterUtils.removeAccent(username + tmp[i].toLowerCase());
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
            if (count > 0) {
                entity.setTaiKhoan(username + count);
            } else {
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

            dataMail.setTo(user.getEmail());
            dataMail.setSubject("Thông tin tài khoản cá nhân");

            Map<String, Object> props = new HashMap<>();
            props.put("name", user.getHoTen());
            props.put("username", username);
            props.put("password", pass);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, "client");
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

    protected void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    @Override
    public void exportFile(HttpServletResponse response) throws IOException {
        String name = "Danh-sach-nhan-vien";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        sheet = workbook.createSheet("Danh sách nhân viên");
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + name + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        writeHeaderLine(workbook, sheet);
        writeDataLines(workbook, sheet);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public ResponseEntity<?> getUser(Long id) {
        List<Object[]> page = userRepository.getUserId(id);
        ArrayList<UserDto> list = new ArrayList<>();
        for (Object[] entity : page) {
            LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[14]);
            list.add(new UserDto(entity[0], entity[1], entity[2], entity[3], entity[4], entity[5], entity[6],
                    entity[7], entity[8], entity[9], (Boolean) entity[10], entity[11], entity[12], entity[13], loaihd.getTenHopDong(),
                    loaihd.getBaoHiem(), entity[15], entity[16]));
        }
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, list));
    }

    @Override
    public ResponseEntity<?> getList(HttpServletRequest request) {
        UserEntity userEntity = jwtUtils.getUserEntity(request);
        List<Object[]> page = userRepository.getList(userEntity.getId());
        String quyen = null;
        UserEntity userEntity1 = userRepository.findById(userEntity.getId()).get();
        for (RoleEntity a : userEntity1.getRoles()){
            quyen = a.getMaQuyen();
        }
        ArrayList<UserLoginDto> list = new ArrayList<>();
        for (Object[] entity : page) {
            list.add(new UserLoginDto(entity[0], entity[1], entity[2], entity[3], entity[4], entity[5], entity[6],
                    entity[7], entity[8], entity[9], entity[10], entity[11],quyen));
        }
        return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, list));
    }

    private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);
        createCell(sheet, row, 0, "STT", style);
        createCell(sheet, row, 1, "Mã nhân viên", style);
        createCell(sheet, row, 2, "Tên nhân viên", style);
        createCell(sheet, row, 3, "Giới tính", style);
        createCell(sheet, row, 4, "Ngày sinh", style);
        createCell(sheet, row, 5, "Số điện thoại", style);
        createCell(sheet, row, 6, "Email", style);
        createCell(sheet, row, 7, "Địa chỉ", style);
        createCell(sheet, row, 8, "CMT", style);
        createCell(sheet, row, 9, "Tên phòng ban", style);
        createCell(sheet, row, 10, "Tên chức vụ", style);
        createCell(sheet, row, 11, "Tính chất nhân viên", style);
        createCell(sheet, row, 12, "Loại hợp đồng", style);
        createCell(sheet, row, 13, "Bảo hiểm", style);
        createCell(sheet, row, 14, "Trạng thái nhân viên", style);

    }

    private void writeDataLines(XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        List<Object[]> list = userRepository.exfort();
        for (Object[] entity : list) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(sheet, row, columnCount++, String.valueOf(rowCount - 1), style);
            createCell(sheet, row, columnCount++, entity[0], style);
            createCell(sheet, row, columnCount++, entity[1], style);
            createCell(sheet, row, columnCount++, entity[2], style);
            createCell(sheet, row, columnCount++, TimeUtil.toDDMMyyyy((Timestamp) entity[3]), style);
            createCell(sheet, row, columnCount++, entity[4], style);
            createCell(sheet, row, columnCount++, entity[5], style);
            createCell(sheet, row, columnCount++, entity[6], style);
            createCell(sheet, row, columnCount++, entity[7], style);
            createCell(sheet, row, columnCount++, entity[9], style);
            createCell(sheet, row, columnCount++, entity[10], style);
            createCell(sheet, row, columnCount++, entity[11], style);
            LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[12]);
            createCell(sheet, row, columnCount++, loaihd.getTenHopDong(), style);
            String baoHiem = loaihd.getBaoHiem() == 1 ? "Có" : "Không có";
            createCell(sheet, row, columnCount++, baoHiem, style);
            String trangThai = String.valueOf(entity[8]) == "true" ? "Hoạt động" : "Nghỉ việc";
            createCell(sheet, row, columnCount++, trangThai, style);
        }
    }
}
