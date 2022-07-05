package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "chuc_vu")
@Data
public class ChucVuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maChucVu;

    private String tenChucVu;

    private String moTa;

    private Integer status;

    @OneToMany(mappedBy = "positions", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<UserEntity> userss;

    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;
}
