package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ly_do")
    @JsonIgnore
    private LyDoEntity lydoss;

    private Date gioVao;
    private Date gioRa;
    private Integer status;
    private String lyDo;
    @CreationTimestamp
    protected Date ngayTao;
    @CreationTimestamp
    protected Date ngaySua;

    protected String nguoiTao;
    protected String nguoiSua;
}
