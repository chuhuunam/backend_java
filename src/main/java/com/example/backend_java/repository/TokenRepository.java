package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM `token` WHERE tai_khoan = :taiKhoan")
    void deleteTaiKhoan(String taiKhoan);

}
