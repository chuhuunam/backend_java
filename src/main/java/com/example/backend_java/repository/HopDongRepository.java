package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.HopDongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopDongRepository extends JpaRepository<HopDongEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT hop_dong.id,user.ho_ten,loai_hop_dong.ten_hop_dong,hop_dong.ma_hop_dong,chuc_vu.ten_chuc_vu,hop_dong.ngay_ky,hop_dong.ngay_hieu_luc,hop_dong.ngay_ket_thuc,hop_dong.luong,DATEDIFF(hop_dong.ngay_ket_thuc,hop_dong.ngay_ky) AS thoi_han,hop_dong.status,hop_dong.mo_ta\n" +
            "FROM hop_dong\n" +
            "JOIN user ON user.id=hop_dong.id_user\n" +
            "JOIN loai_hop_dong ON loai_hop_dong.id= hop_dong.id_loai_hop_dong\n" +
            "JOIN chuc_vu ON chuc_vu.id = hop_dong.id_chuc_vu\n" +
            "LIMIT :limit,:offset")
    List<Object[]> getHopDong(int limit, int offset);

    @Query(nativeQuery = true, value ="SELECT id FROM hop_dong where id=(select max(id) from hop_dong);")
    Integer getId();
}
