package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.PhongBanEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class PhongBanDto {

    private Long id;
    private String id_department;
    private String name_department;
    private String describe;
    private boolean status;
    protected String create_at;
    protected String update_at;
    protected String create_by;
    protected String update_by;

    public void  fromEntity (PhongBanEntity entity) {
        this.id = entity.getId();
        this.id_department = entity.getMaPhongBan();
        this.name_department = entity.getTenPhongBan();
        this.describe = entity.getMoTa();
        this.status = entity.isStatus();
        this.create_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.update_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.create_by = entity.getNguoiTao();
        this.update_by = entity.getNguoiSua();
    }
}
