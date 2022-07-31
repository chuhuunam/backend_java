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

    Integer countByStatus(Boolean status);

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
            "AND user.status = 1 \n" +
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
            "AND user.status = 1 \n" +
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
            "AND user.status = 1 \n" +
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
            "AND user.status = 1 \n" +
            "AND hop_dong.status = 1")
    Long TotalUser1(String keyword, Integer idPhongBan, Integer idChucVu,Integer idLoaiHopDong,String sex);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user WHERE user.tai_khoan=:username")
    Integer Count(String username);

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,dia_chi,cmt,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,user.ngay_tao,user.ngay_sua \n" +
            "FROM user \n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban \n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "WHERE user.status = 1")
    List<Object[]> exfort();

    @Query(nativeQuery = true, value = "SELECT user.id,tai_khoan,ho_ten,gioi_tinh,ngay_sinh,so_dien_thoai,email,anh_dai_dien,dia_chi,cmt,user.status,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,user.ngay_tao,user.ngay_sua \n" +
            "from user\n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "AND user.id =:id")
    List<Object[]> getUserId(Long id);

    @Query(nativeQuery = true,value = "select count(*) as count,\"Dưới 1 năm\" AS title FROM user where DATEDIFF(CURDATE(), ngay_tao) < 365\n" +
            "union all \n" +
            "select count(*) as count,\"Từ 1-3 năm\" AS title FROM user \n" +
            "where DATEDIFF(CURDATE(), ngay_tao)/ 365 > 1\n" +
            "and DATEDIFF(CURDATE(), ngay_tao)/ 365 < 3\n" +
            "union all \n" +
            "select count(*) as count,\"Từ 3-5 năm\" AS title FROM user\n" +
            "where DATEDIFF(CURDATE(), ngay_tao)/ 365 > 3\n" +
            "and DATEDIFF(CURDATE(), ngay_tao)/ 365 < 5\n" +
            "union all \n" +
            "select count(*) as count,\"Trên 5 năm\" AS title FROM user where DATEDIFF(CURDATE(), ngay_tao)/ 365 > 5")
    List<Object[]> pie();

    @Query(nativeQuery = true,value = " SELECT count(*) as count,\"Hợp đồng chính thức\" AS title FROM user\n" +
            "    join hop_dong on hop_dong.id_user = user.id\n" +
            "    where hop_dong.status = true\n" +
            "    and hop_dong.id_loai_hop_dong = 1\n" +
            "    UNION ALL\n" +
            "    SELECT count(*) as count,\"Hợp đồng học việc\" AS title FROM user\n" +
            "    join hop_dong on hop_dong.id_user = user.id\n" +
            "    where hop_dong.status = true\n" +
            "    and hop_dong.id_loai_hop_dong = 2\n" +
            "    UNION ALL\n" +
            "    SELECT count(*) as count,\"Hợp đồng thử việc\" AS title FROM user\n" +
            "    join hop_dong on hop_dong.id_user = user.id\n" +
            "    where hop_dong.status = true\n" +
            "    and hop_dong.id_loai_hop_dong = 3\n" +
            "    UNION ALL\n" +
            "    SELECT count(*) as count,\"Hợp đồng khác\" AS title FROM user\n" +
            "    join hop_dong on hop_dong.id_user = user.id\n" +
            "    where hop_dong.status = true\n" +
            "    and hop_dong.id_loai_hop_dong != 1\n" +
            "    and hop_dong.id_loai_hop_dong != 2\n" +
            "    and hop_dong.id_loai_hop_dong != 3")
    List<Object[]> contractChart();

    @Query(nativeQuery = true,value = "SELECT user.id,tai_khoan,ho_ten,phong_ban.ten_phong_ban,chuc_vu.ten_chuc_vu,ngay_nghi,ly_do_nghi,hop_dong.tinh_chat_lao_dong FROM user\n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE user.status = 0\n" +
            "AND (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.ly_do_nghi,'') LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "and hop_dong.status = 1\n" +
            "ORDER BY `user`.`id` DESC\n" +
            "LIMIT :limit,:offset")
    List<Object[]> nvNghiViec(String keyword, Integer idPhongBan, Integer idChucVu,int limit, int offset);

    @Query(nativeQuery = true,value = "SELECT COUNT(*) FROM user\n" +
            "JOIN phong_ban ON phong_ban.id = user.id_phong_ban\n" +
            "JOIN chuc_vu ON chuc_vu.id = user.id_chuc_vu\n" +
            "JOIN hop_dong ON hop_dong.id_user = user.id\n" +
            "WHERE user.status = 0\n" +
            "AND (:keyword is null or CONCAT(user.tai_khoan,'',user.ho_ten,'',user.ly_do_nghi,'') LIKE  %:keyword% )\n" +
            "AND (:idPhongBan is null or user.id_phong_ban = :idPhongBan)\n" +
            "AND (:idChucVu is null or user.id_chuc_vu = :idChucVu)\n" +
            "and hop_dong.status = 1")
    Long Total(String keyword, Integer idPhongBan, Integer idChucVu);
}
