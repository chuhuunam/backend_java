package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class LoaiHopDongDto {
    private Long id;
    private String tenHopDong;
    private String loaiHopDong;
    private Integer baoHiem;
    private boolean status;
    protected String ngayTao;
    protected String ngaySua;
    protected String nguoiTao;
    protected String nguoiSua;

    public void  fromEntity (LoaiHopDongEntity entity) {
        this.id = entity.getId();
        this.tenHopDong = entity.getTenHopDong();
        this.loaiHopDong=entity.getLoaiHopDong();
        this.baoHiem=entity.getBaoHiem();
        this.status = entity.isStatus();
        this.ngayTao = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.ngaySua = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.nguoiTao = entity.getNguoiTao();
        this.nguoiSua = entity.getNguoiSua();
    }
}
