package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class updateDepRequest {
    private Integer id_phong_ban;

    public ResponseEntity<?> validate() {
        if (id_phong_ban == null || id_phong_ban < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_phong_ban"));
        }
        return null;
    }
}
