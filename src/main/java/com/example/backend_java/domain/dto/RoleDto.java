package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.RoleEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String id_role;
    private String name_role;
    private String describe;
    private boolean status;
    protected String create_at;
    protected String update_at;
    protected String create_by;
    protected String update_by;

    public void  fromEntity (RoleEntity entity) {
        this.id = entity.getId();
        this.id_role = entity.getMaQuyen();
        this.name_role = entity.getTenQuyen();
        this.describe = entity.getMoTa();
        this.status = entity.isStatus();
        this.create_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.update_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.create_by = entity.getNguoiTao();
        this.update_by = entity.getNguoiSua();
    }
}
