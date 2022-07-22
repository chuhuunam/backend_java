package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserLoginDto {
    private Object id;
    private Object maNhanVien;
    private Object hoTen;
    private Object gioiTinh;
    private Object ngaySinh;
    private Object soDienThoai;
    private Object email;
    private Object anhDaiDien;
    private Object diaChi;
    private Object cmt;
    private Object tenPhongBan;
    private Object tenChucVu;
    private Object quyen;

    public UserLoginDto(Object id, Object maNhanVien, Object hoTen, Object gioiTinh, Object ngaySinh, Object soDienThoai, Object email, Object anhDaiDien, Object diaChi, Object cmt, Object tenPhongBan, Object tenChucVu, Object quyen) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        TimeUtil.toDDMMyyyy((Timestamp) ngaySinh);
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.anhDaiDien = anhDaiDien;
        this.diaChi = diaChi;
        this.cmt = cmt;
        this.tenPhongBan = tenPhongBan;
        this.tenChucVu = tenChucVu;
        this.quyen = quyen;
    }

}
