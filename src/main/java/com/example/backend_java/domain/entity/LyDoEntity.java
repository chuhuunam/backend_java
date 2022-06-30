package com.example.backend_java.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ly_do")
public class LyDoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer id_cha;
    private String lyDo;
    private Integer huongLuong;
    private Integer status;
    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;
    protected String nguoiTao;
    protected String nguoiSua;
}
