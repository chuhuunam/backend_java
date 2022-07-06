package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.RoleEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String maQuyen;
    private String tenQuyen;
    private String moTa;
    private boolean status;
    protected String ngayTao;
    protected String ngaySua;
    protected String nguoiTao;
    protected String nguoiSua;

    public void  fromEntity (RoleEntity entity) {
        this.id = entity.getId();
        this.maQuyen = entity.getMaQuyen();
        this.tenQuyen = entity.getTenQuyen();
        this.moTa = entity.getMoTa();
        this.status = entity.isStatus();
        this.ngayTao = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.ngaySua = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.nguoiTao = entity.getNguoiTao();
        this.nguoiSua = entity.getNguoiSua();
    }
}
