package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.LyDoEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class LyDoDto {
    private Long id;
    private String reason;
    private String father_reason;
    private Integer salary;
    private boolean status;
    protected String create_at;
    protected String update_at;
    protected String create_by;
    protected String update_by;

    public LyDoDto() {
    }

    public LyDoDto(Long id, String reason, String father_reason, Integer salary, boolean status, String create_at, String update_at, String create_by, String update_by) {
        this.id = id;
        this.reason = reason;
        this.father_reason = father_reason;
        this.salary = salary;
        this.status = status;
        this.create_at = create_at;
        this.update_at = update_at;
        this.create_by = create_by;
        this.update_by = update_by;
    }
    public void  fromEntity (LyDoEntity entity) {
        this.id = entity.getId();
        this.reason=entity.getLyDo();
        this.salary=entity.getHuongLuong();
        this.status = entity.isStatus();
        this.create_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.update_at = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.create_by = entity.getNguoiTao();
        this.update_by = entity.getNguoiSua();
    }
}
