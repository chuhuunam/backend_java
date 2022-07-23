package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.ChucVuEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class ChucVuDto {
    private Long id;
    private String id_position;
    private String name_position;
    private String describe;
    private boolean status;
    protected String create_at;
    protected String update_at;
    protected String create_by;
    protected String update_by;

    public void  fromEntity (ChucVuEntity entity) {
        this.id = entity.getId();
        this.id_position = entity.getMaChucVu();
        this.name_position = entity.getTenChucVu();
        this.describe = entity.getMoTa();
        this.status = entity.isStatus();
        this.create_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.update_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.create_by = entity.getNguoiTao();
        this.update_by = entity.getNguoiSua();
    }
}
