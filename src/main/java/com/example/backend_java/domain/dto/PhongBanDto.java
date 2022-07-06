package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.PhongBanEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class PhongBanDto {

    private Long id;
    private String maPhongBan;
    private String tenPhongBan;
    private String moTa;
    private boolean status;
    protected String ngayTao;
    protected String ngaySua;
    protected String nguoiTao;
    protected String nguoiSua;

    public void  fromEntity (PhongBanEntity entity) {
        this.id = entity.getId();
        this.maPhongBan = entity.getMaPhongBan();
        this.tenPhongBan = entity.getTenPhongBan();
        this.moTa = entity.getMoTa();
        this.status = entity.isStatus();
        this.ngayTao = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.ngaySua = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.nguoiTao = entity.getNguoiTao();
        this.nguoiSua = entity.getNguoiSua();
    }
}
