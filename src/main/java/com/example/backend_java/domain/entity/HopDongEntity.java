package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hop_dong")
@Data
public class HopDongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String maHopDong;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_loai_hop_dong")
    @JsonIgnore
    private LoaiHopDongEntity loaihopdong;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserEntity nguoidung;

    private Float luong;
    private Date ngayKy;
    private Date ngayHieuLuc;
    private Date ngayKetThuc;
    private boolean status;
    private String moTa;
    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;

}
