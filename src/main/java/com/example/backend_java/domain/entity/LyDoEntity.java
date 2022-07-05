package com.example.backend_java.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
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

    @OneToMany(mappedBy = "lydos", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<ChamCongEntity> chamcong;
}
