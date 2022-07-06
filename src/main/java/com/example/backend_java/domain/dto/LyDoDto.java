package com.example.backend_java.domain.dto;

import com.example.backend_java.domain.entity.LyDoEntity;
import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

@Data
public class LyDoDto {
    private Long id;
    private String LyDo;
    private String lyDoCha;
    private Integer huongLuong;
    private boolean status;
    protected String nguoiTao;
    protected String nguoiSua;
    protected String ngayTao;
    protected String ngaySua;

    public LyDoDto() {
    }

    public LyDoDto(Long id, String lyDo, String lyDoCha, Integer huongLuong, boolean status, String nguoiTao, String nguoiSua, String ngayTao, String ngaySua) {
        this.id = id;
        LyDo = lyDo;
        this.lyDoCha = lyDoCha;
        this.huongLuong = huongLuong;
        this.status = status;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
    }

    public void  fromEntity (LyDoEntity entity) {
        this.id = entity.getId();
        this.LyDo=entity.getLyDo();
        this.huongLuong=entity.getHuongLuong();
        this.status = entity.isStatus();
        this.ngayTao = TimeUtil.toHHmmDDMMyyyy(entity.getNgayTao());
        this.ngaySua = TimeUtil.toHHmmDDMMyyyy(entity.getNgaySua());
        this.nguoiTao = entity.getNguoiTao();
        this.nguoiSua = entity.getNguoiSua();
    }
}
