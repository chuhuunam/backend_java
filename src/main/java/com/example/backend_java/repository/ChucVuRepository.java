package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.ChucVuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVuEntity,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM chuc_vu WHERE " +
            "CONCAT(chuc_vu.ten_chuc_vu,'',chuc_vu.ma_chuc_vu,'',chuc_vu.mo_ta,'' )" +
            "LIKE %?1%")
    Page<ChucVuEntity> findAll(String keyword, Pageable pageable);
}
