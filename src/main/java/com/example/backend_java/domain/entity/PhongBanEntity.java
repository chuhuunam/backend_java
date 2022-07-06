package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "phong_ban")
@Data
public class PhongBanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maPhongBan;

    private String tenPhongBan;

    private String moTa;

    private boolean status;

    @OneToMany(mappedBy = "departments", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<UserEntity> users;

    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;
}
