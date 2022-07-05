package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class HopDongDTO {
    private Object id;
    private Object hoTen;
    private Object tenHopDong;
    private Object maHopDong;
    private Object tenChucVu;
    private Object tenPhongBan;
    private Object ngayKy;
    private Object ngayHieuLuc;
    private Object ngayKetThuc;
    private Float luong;
    private Object thoiHan;
    private Integer status;
    private Object moTa;
    private Integer baoHiem;

    public HopDongDTO(Object id, Object hoTen, Object tenHopDong, Object maHopDong, Object tenChucVu, Object tenPhongBan, Object ngayKy, Object ngayHieuLuc, Object ngayKetThuc, Float luong, Object thoiHan, Integer status, Object moTa, Integer baoHiem) {
        this.id = id;
        this.hoTen = hoTen;
        this.tenHopDong = tenHopDong;
        this.maHopDong = maHopDong;
        this.tenChucVu = tenChucVu;
        this.tenPhongBan = tenPhongBan;
        this.ngayKy = TimeUtil.toDDMMyyyy((Timestamp) ngayKy);
        this.ngayHieuLuc = TimeUtil.toDDMMyyyy((Timestamp) ngayHieuLuc);
        this.ngayKetThuc = TimeUtil.toDDMMyyyy((Timestamp) ngayKetThuc);
        this.luong = luong;
        this.thoiHan = thoiHan;
        this.status = status;
        this.moTa = moTa;
        this.baoHiem = baoHiem;
    }
}
