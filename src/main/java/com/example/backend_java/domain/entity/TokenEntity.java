package com.example.backend_java.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taiKhoan;

    private String token;

    @CreationTimestamp
    protected Date ngayTao;

    private Date NgayHetHan;
}
