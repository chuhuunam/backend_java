package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "loai_hop_dong")
@Data
public class LoaiHopDongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String tenHopDong;
    private String loaiHopDong;
    private Integer baoHiem;
    private boolean status;
    @OneToMany(mappedBy = "loaihopdong", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<HopDongEntity> hopdong;

    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;

}
