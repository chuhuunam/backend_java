package com.example.backend_java.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "cham_cong")
public class ChamCongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;
}
