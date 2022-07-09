package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByTaiKhoan(String taiKhoan);
    UserEntity findByTaiKhoanAndStatus(String taiKhoan,boolean Status);
    boolean existsAllByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,hop_dong.tinh_chat_lao_dong,hop_dong.id_loai_hop_dong,hop_dong.ngay_hieu_luc,hop_dong.ngay_ket_thuc\n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE hop_dong.status = 1 \n" +
            "AND (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.so_dien_thoai,'',user.email,'',user.dia_chi,'',user.cmt,'' ) LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "ORDER BY `user`.`id` DESC \n" +
            "LIMIT :limit,:offset")
    List<Object[]> getUser(String keyword, Integer idPhongBan, Integer idChucVu, int limit, int offset);



    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user WHERE user.tai_khoan=:username")
    Integer Count(String username);
}
