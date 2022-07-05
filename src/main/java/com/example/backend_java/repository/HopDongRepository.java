package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.HopDongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopDongRepository extends JpaRepository<HopDongEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT hop_dong.id,user.ho_ten,hop_dong.id_loai_hop_dong,hop_dong.ma_hop_dong,chuc_vu.ten_chuc_vu,phong_ban.ten_phong_ban,hop_dong.ngay_ky,hop_dong.ngay_hieu_luc,hop_dong.ngay_ket_thuc,hop_dong.luong,DATEDIFF(hop_dong.ngay_ket_thuc,hop_dong.ngay_ky) AS thoi_han,hop_dong.status,hop_dong.mo_ta\n" +
            "FROM user \n" +
            "JOIN hop_dong ON user.id=hop_dong.id_user \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "WHERE (:tenNhanVien is null or lower(user.ho_ten) like lower(concat(concat('%', :tenNhanVien),'%')))\n" +
            "AND (:maLoaiHopDong is null or hop_dong.ma_hop_dong = :maLoaiHopDong)\n" +
            "AND (:status is null or hop_dong.status = :status)\n" +
            "LIMIT :limit,:offset")
    List<Object[]> getHopDong(String tenNhanVien, Integer maLoaiHopDong, Integer status, int limit, int offset);

    @Query(nativeQuery = true, value ="SELECT id FROM hop_dong where id=(select max(id) from hop_dong);")
    Integer getId();

    @Query(nativeQuery = true,value = "SELECT * FROM `hop_dong` WHERE hop_dong.status = 1 AND DATEDIFF(hop_dong.ngay_ket_thuc,CURDATE()) < 0;")
    List<HopDongEntity> checkStatus();

    @Query(nativeQuery = true, value = "SELECT user.ho_ten,hop_dong.id_loai_hop_dong,hop_dong.ma_hop_dong,chuc_vu.ten_chuc_vu,phong_ban.ten_phong_ban,hop_dong.ngay_ky,hop_dong.ngay_hieu_luc,hop_dong.ngay_ket_thuc,hop_dong.luong,DATEDIFF(hop_dong.ngay_ket_thuc,hop_dong.ngay_ky) AS thoi_han,hop_dong.status,hop_dong.mo_ta\n" +
            "FROM user \n" +
            "JOIN hop_dong ON user.id=hop_dong.id_user \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban")
    List<Object[]> exfort();
}
