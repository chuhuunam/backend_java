package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.PhongBanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBanEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM phong_ban WHERE ten_phong_ban LIKE %:tenPhongBan%")
    Page<PhongBanEntity> findAll(String tenPhongBan, Pageable pageable);
}
