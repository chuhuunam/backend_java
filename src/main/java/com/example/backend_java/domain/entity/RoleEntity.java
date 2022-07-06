package com.example.backend_java.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maQuyen;

    private String tenQuyen;

    @Column(length = 500)
    private String moTa;

    private boolean status;

    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;

}

