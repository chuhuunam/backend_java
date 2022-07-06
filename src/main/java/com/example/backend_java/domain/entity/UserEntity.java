package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String hoTen;

    private String taiKhoan;

    private String matKhau;

    private String anhDaiDien;

    private String gioiTinh;

    private String soDienThoai;

    private String diaChi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_phong_ban") // thông qua khóa ngoại id_department
    @JsonIgnore
    private PhongBanEntity departments;

    @OneToMany(mappedBy = "nguoidung", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<HopDongEntity> hopdongs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_chuc_vu") // thông qua khóa ngoại id_position
    @JsonIgnore
    private ChucVuEntity positions;

    private boolean status;

    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

}
