package com.example.backend_java.domain.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserRequest {
    private String email;
    private String hoTen;
    private String taiKhoan;
    private String matKhau;
    private String anhDaiDien;
    private String gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String cmt;
    private Date ngaySinh;
    private boolean status;
    private Integer id_phong_ban;
    private Integer id_chuc_vu;
    private Set<String> roles;
}
