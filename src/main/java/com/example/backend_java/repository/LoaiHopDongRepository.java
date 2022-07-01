package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiHopDongRepository extends JpaRepository<LoaiHopDongEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM `loai_hop_dong` " +
            "WHERE (:tenHopDong is null or lower(loai_hop_dong.ten_hop_dong) like lower(concat(concat('%', :tenHopDong),'%'))) " +
            "AND (:loaiHopDong is null or lower(loai_hop_dong.loai_hop_dong) like lower(concat(concat('%', :loaiHopDong),'%')))" +
            "AND (:baoHiem is null or loai_hop_dong.bao_hiem = :baoHiem)")
    Page<LoaiHopDongEntity> search(String tenHopDong,String loaiHopDong,Integer baoHiem, Pageable pageable);
}
