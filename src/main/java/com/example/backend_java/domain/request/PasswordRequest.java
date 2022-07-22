package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class PasswordRequest {
    private String mat_khau;

    public ResponseEntity<?> validate() {
        if (Strings.isNullOrEmpty(mat_khau)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập email"));
        }
        return null;
    }
}
