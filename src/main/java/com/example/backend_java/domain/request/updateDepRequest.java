package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class updateDepRequest {
    private Integer id_department;

    public ResponseEntity<?> validate() {
        if (id_department == null || id_department < 0) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_phong_ban"));
        }
        return null;
    }
}
