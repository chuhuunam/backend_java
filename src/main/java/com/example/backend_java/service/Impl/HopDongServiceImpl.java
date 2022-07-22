package com.example.backend_java.service.Impl;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.dto.HopDongDTO;
import com.example.backend_java.domain.entity.HopDongEntity;
import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.HopDongRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.PageResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.ChucVuRepository;
import com.example.backend_java.repository.HopDongRepository;
import com.example.backend_java.repository.LoaiHopDongRepository;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.HopDongService;
import com.example.backend_java.utils.JwtUtils;
import com.example.backend_java.utils.TimeUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public ResponseEntity<?> getPage(String tenNhanVien, Integer maLoaiHopDong, Integer status, Integer index, Integer size) {
        try {
            Integer offset = (index - 1) * size;
            List<Object[]> page = hopDongRepository.getHopDong(tenNhanVien, maLoaiHopDong, status, offset, size);
            ArrayList<HopDongDTO> list = new ArrayList<>();
            for (Object[] entity : page) {
                LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[2]);
                list.add(new HopDongDTO(entity[0], entity[1], loaihd.getTenHopDong(), entity[3], entity[4], entity[5], entity[6],
                        entity[7], entity[8], (Float) entity[9], entity[10], (Boolean) entity[11], entity[12], loaihd.getBaoHiem()));
            }
            PageResponse<HopDongEntity> data = new PageResponse(index, size, (long) page.size(), list);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, data));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Phân trang hợp đồng lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> createHopDong(HttpServletRequest request, HopDongRequest hopdong) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            List<HopDongEntity> check = hopDongRepository.checkCreate(hopdong.getId_user());
            if (check == null) {
                HopDongEntity entity = new HopDongEntity();
                Integer stt = hopDongRepository.getId();
                if (stt == null) {
                    stt = 1;
                } else {
                    stt = stt + 1;
                }
                LoaiHopDongEntity loaihopdong = loaiHopDongRepository.findById(Long.valueOf(hopdong.getId_loai_hop_dong())).get();
                entity.setLoaihopdong(loaihopdong);
                UserEntity user = userRepository.findById(Long.valueOf(hopdong.getId_user())).get();
                entity.setNguoidung(user);
                entity.setMaHopDong("HD_" + stt);
                entity.setNgayKy(hopdong.getNgayKy());
                entity.setNgayHieuLuc(hopdong.getNgayHieuLuc());
                entity.setNgayKetThuc(hopdong.getNgayKetThuc());
                entity.setLuong(hopdong.getLuong());
                entity.setStatus(true);
                entity.setMoTa(hopdong.getMoTa());
                entity.setNguoiTao(userEntity.getHoTen());
                hopDongRepository.save(entity);
                return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Thêm hợp đồng thành công"));
            } else {
                return ResponseEntity.ok(new ErrResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Nhân viên vẫn còn hợp đồng"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Thêm hợp đồng lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> updateHopDong(HttpServletRequest request, HopDongRequest req, Long id) {
        try {
            UserEntity userEntity = jwtUtils.getUserEntity(request);
            HopDongEntity entity = hopDongRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            LoaiHopDongEntity loaihopdong = loaiHopDongRepository.findById(Long.valueOf(req.getId_loai_hop_dong())).get();
            entity.setLoaihopdong(loaihopdong);
            UserEntity user = userRepository.findById(Long.valueOf(req.getId_user())).get();
            entity.setNguoidung(user);
            entity.setNgayHieuLuc(req.getNgayHieuLuc());
            entity.setNgayKetThuc(req.getNgayKetThuc());
            entity.setLuong(req.getLuong());
            entity.setStatus(req.isStatus());
            entity.setMoTa(req.getMoTa());
            entity.setNguoiSua(userEntity.getHoTen());
            hopDongRepository.save(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Sửa hợp đồng thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Sửa hợp đồng lỗi"));
        }
    }

    @Override
    public ResponseEntity<?> deleteHopDong(Long id) {
        try {
            HopDongEntity entity = hopDongRepository.findById(id).orElse(null);
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy id"));
            }
            hopDongRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa hợp đồng thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Xóa hợp đồng lỗi"));
        }
    }

    @Override
    public void UpdateTime() {
        List<HopDongEntity> hopDong = hopDongRepository.checkStatus();
        for (HopDongEntity entity : hopDong) {
            HopDongEntity hopDongEntity = hopDongRepository.findById(entity.getId()).orElse(null);
            hopDongEntity.setStatus(false);
            hopDongRepository.save(hopDongEntity);
        }
    }

    @Override
    public void exportFile(HttpServletResponse response) throws IOException {
        String name = "Danh-sach-hop-dong";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet;
        sheet = workbook.createSheet("Danh sách hợp đồng");
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

    private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        createCell(sheet, row, 0, "STT", style);
        createCell(sheet, row, 1, "Họ và Tên", style);
        createCell(sheet, row, 2, "Mã hợp đồng", style);
        createCell(sheet, row, 3, "Tên hợp đồng", style);
        createCell(sheet, row, 4, "Tên chức vụ", style);
        createCell(sheet, row, 5, "Tên phòng ban ", style);
        createCell(sheet, row, 6, "Ngày ký", style);
        createCell(sheet, row, 7, "Ngày hiệu lực", style);
        createCell(sheet, row, 8, "Ngày kết thúc", style);
        createCell(sheet, row, 9, "Lương", style);
        createCell(sheet, row, 10, "Thời hạn", style);
        createCell(sheet, row, 11, "Trạng thái hợp đồng", style);
        createCell(sheet, row, 12, "Bảo hiểm", style);
        createCell(sheet, row, 13, "Mô tả", style);
    }

    private void writeDataLines(XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        List<Object[]> list = hopDongRepository.exfort();
        for (Object[] entity : list) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            LoaiHopDongEntity loaihd = loaiHopDongRepository.Name((BigInteger) entity[1]);
            createCell(sheet, row, columnCount++, String.valueOf(rowCount - 1), style);
            createCell(sheet, row, columnCount++, entity[0], style);
            createCell(sheet, row, columnCount++, entity[2], style);
            createCell(sheet, row, columnCount++, loaihd.getTenHopDong(), style);
            createCell(sheet, row, columnCount++, entity[3], style);
            createCell(sheet, row, columnCount++, entity[4], style);
            createCell(sheet, row, columnCount++, TimeUtil.toDDMMyyyy((Timestamp) entity[5]), style);
            createCell(sheet, row, columnCount++, TimeUtil.toDDMMyyyy((Timestamp) entity[6]), style);
            createCell(sheet, row, columnCount++, TimeUtil.toDDMMyyyy((Timestamp) entity[7]), style);
            createCell(sheet, row, columnCount++, entity[8], style);
            String thoiHan = String.valueOf(entity[9]);
            createCell(sheet, row, columnCount++, thoiHan, style);
            String trangThai = String.valueOf(entity[10]) == "true" ? "Còn thời hạn" : "Hết thời hạn";
            createCell(sheet, row, columnCount++, trangThai, style);
            String baoHiem = loaihd.getBaoHiem() == 1 ? "Có" : "Không có";
            createCell(sheet, row, columnCount++, baoHiem, style);
            createCell(sheet, row, columnCount++, entity[11], style);
        }
    }
}
