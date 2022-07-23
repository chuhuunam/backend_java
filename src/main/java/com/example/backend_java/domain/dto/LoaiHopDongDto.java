package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class LoaiHopDongDto {
    private Long id;
    private String name_contract;
    private Integer insurance;
    private boolean status;
    protected String create_at;
    protected String update_at;
    protected String create_by;
    protected String update_by;

    public void  fromEntity (LoaiHopDongEntity entity) {
        this.id = entity.getId();
        this.name_contract = entity.getTenHopDong();
        this.insurance=entity.getBaoHiem();
        this.status = entity.isStatus();
        this.create_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.update_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.create_by = entity.getNguoiTao();
        this.update_by = entity.getNguoiSua();
    }
}
