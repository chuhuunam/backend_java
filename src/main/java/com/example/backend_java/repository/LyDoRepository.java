package com.example.backend_java.repository;

import com.example.backend_java.domain.entity.LyDoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LyDoRepository extends JpaRepository<LyDoEntity,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM ly_do WHERE id_cha LIKE %:id_cha%")
    List<LyDoEntity> findById_cha(Integer id_cha);

    @Query(nativeQuery = true, value = "SELECT * FROM `ly_do` WHERE (:lyDo is null or lower(ly_do.ly_do) like lower(concat(concat('%', :lyDo),'%'))) AND (:idCha is null or ly_do.id_cha = :idCha)")
    Page<LyDoEntity> search(String lyDo, Integer idCha, Pageable pageable);
}
