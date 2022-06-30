package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByTaiKhoan(String taiKhoan);
    UserEntity findByTaiKhoanAndStatus(String taiKhoan,Integer Status);
}
