package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.ChucVuEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class ChucVuDto {
    private Long id;
    private String maChucVu;
    private String tenChucVu;
    private String moTa;
    private Integer status;
    protected String ngayTao;
    protected String ngaySua;
    protected String nguoiTao;
    protected String nguoiSua;

    public void  fromEntity (ChucVuEntity entity) {
        this.id = entity.getId();
        this.maChucVu = entity.getMaChucVu();
        this.tenChucVu = entity.getTenChucVu();
        this.moTa = entity.getMoTa();
        this.status = entity.getStatus();
        this.ngayTao = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.ngaySua = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.nguoiTao = entity.getNguoiTao();
        this.nguoiSua = entity.getNguoiSua();
    }
}
