package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByTaiKhoan(String taiKhoan);

    boolean existsAllByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu\n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "WHERE user.id =:id")
    List<Object[]> getList(Long id);

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,user.ngay_tao,user.ngay_sua \n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "WHERE (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.so_dien_thoai,'',user.email,'',user.dia_chi,'',user.cmt,'' ) LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "AND (:sex is null or user.gioi_tinh = :sex)\n" +
            "ORDER BY `user`.`id` DESC \n" +
            "LIMIT :limit,:offset")
    List<Object[]> getUser(String keyword, Integer idPhongBan, Integer idChucVu,String sex, int limit, int offset);

    @Query(nativeQuery = true, value = "SELECT count(*) \n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "WHERE (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.so_dien_thoai,'',user.email,'',user.dia_chi,'',user.cmt,'' ) LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "AND (:sex is null or user.gioi_tinh = :sex)")
    Long TotalUser(String keyword, Integer idPhongBan, Integer idChucVu,String sex);

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,user.ngay_tao,user.ngay_sua \n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.so_dien_thoai,'',user.email,'',user.dia_chi,'',user.cmt,'' ) LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "AND (:sex is null or user.gioi_tinh = :sex)\n" +
            "AND (:idLoaiHopDong is null or hop_dong.id_loai_hop_dong = :idLoaiHopDong)\n" +
            "AND hop_dong.status = 1 \n" +
            "ORDER BY `user`.`id` DESC \n" +
            "LIMIT :limit,:offset")
    List<Object[]> getUser1(String keyword, Integer idPhongBan, Integer idChucVu,Integer idLoaiHopDong,String sex, int limit, int offset);
    @Query(nativeQuery = true, value = "SELECT count(*) \n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.so_dien_thoai,'',user.email,'',user.dia_chi,'',user.cmt,'' ) LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "AND (:sex is null or user.gioi_tinh = :sex)\n" +
            "AND (:idLoaiHopDong is null or hop_dong.id_loai_hop_dong = :idLoaiHopDong)\n" +
            "AND hop_dong.status = 1")
    Long TotalUser1(String keyword, Integer idPhongBan, Integer idChucVu,Integer idLoaiHopDong,String sex);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user WHERE user.tai_khoan=:username")
    Integer Count(String username);

    @Query(nativeQuery = true, value = "SELECT tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,hop_dong.tinh_chat_lao_dong,hop_dong.id_loai_hop_dong\n" +
            "FROM user\n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE hop_dong.status=1")
    List<Object[]> exfort();

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,user.ngay_tao,user.ngay_sua \n" +
            "from user\n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "AND user.id =:id")
    List<Object[]> getUserId(Long id);
}
