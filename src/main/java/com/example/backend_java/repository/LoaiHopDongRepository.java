package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.LoaiHopDongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface LoaiHopDongRepository extends JpaRepository<LoaiHopDongEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM `loai_hop_dong` " +
            "WHERE (:tenHopDong is null or lower(loai_hop_dong.ten_hop_dong) like lower(concat(concat('%', :tenHopDong),'%'))) " +
//            "AND (:loaiHopDong is null or lower(loai_hop_dong.loai_hop_dong) like lower(concat(concat('%', :loaiHopDong),'%')))" +
            "AND (:baoHiem is null or loai_hop_dong.bao_hiem = :baoHiem)")
    Page<LoaiHopDongEntity> search(String tenHopDong,Integer baoHiem, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM loai_hop_dong WHERE id LIKE %:id%")
    LoaiHopDongEntity Name(BigInteger id);

    @Query(nativeQuery = true, value = "SELECT hop_dong.tinh_chat_lao_dong,loai_hop_dong.ten_hop_dong,loai_hop_dong.bao_hiem FROM `hop_dong` \n" +
            "JOIN loai_hop_dong ON loai_hop_dong.id = hop_dong.id_loai_hop_dong\n" +
            "WHERE hop_dong.id_user = :id\n" +
            "AND hop_dong.status = 1\n" +
            "AND (:idLoaiHopDong is null or hop_dong.id_loai_hop_dong = :idLoaiHopDong)")
    List<Object[]> UserContract(BigInteger id,Integer idLoaiHopDong);


    @Query(nativeQuery = true, value = "SELECT hop_dong.tinh_chat_lao_dong,loai_hop_dong.ten_hop_dong,loai_hop_dong.bao_hiem FROM `hop_dong` \n" +
            "JOIN loai_hop_dong ON loai_hop_dong.id = hop_dong.id_loai_hop_dong\n" +
            "WHERE hop_dong.id_user = :id\n" +
            "AND hop_dong.status = 1")
    List<Object[]> Contract(BigInteger id);

    @Query(nativeQuery = true, value = "SELECT hop_dong.tinh_chat_lao_dong,loai_hop_dong.ten_hop_dong,loai_hop_dong.bao_hiem FROM `hop_dong` \n" +
            "JOIN loai_hop_dong ON loai_hop_dong.id = hop_dong.id_loai_hop_dong\n" +
            "WHERE hop_dong.id_user = :id\n" +
            "AND hop_dong.status = 1")
    List<Object[]> UserContract(BigInteger id);
}
