package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
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
    private boolean status;
    private Object tenPhongBan;
    private Object tenChucVu;
    private Object tinhChatLaoDong;
    private Object loaiHopDong;
    private Object baoHiem;
    private Object ngayHieuLuc;
    private Object ngayKetThuc;

    public UserDto(Object id, Object maNhanVien, Object hoTen, Object gioiTinh, Object ngaySinh, Object soDienThoai, Object email, Object anhDaiDien, Object diaChi, Object cmt, boolean status, Object tenPhongBan, Object tenChucVu, Object tinhChatLaoDong, Object loaiHopDong, Object baoHiem, Object ngayHieuLuc, Object ngayKetThuc) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = TimeUtil.toDDMMyyyy((Timestamp) ngaySinh);
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.anhDaiDien = anhDaiDien;
        this.diaChi = diaChi;
        this.cmt = cmt;
        this.status = status;
        this.tenPhongBan = tenPhongBan;
        this.tenChucVu = tenChucVu;
        this.tinhChatLaoDong = tinhChatLaoDong;
        this.loaiHopDong = loaiHopDong;
        this.baoHiem = baoHiem;
        this.ngayHieuLuc = TimeUtil.toDDMMyyyy((Timestamp)ngayHieuLuc);
        this.ngayKetThuc = TimeUtil.toDDMMyyyy((Timestamp)ngayKetThuc);
    }
}
