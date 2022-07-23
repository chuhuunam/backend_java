package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ChucVuRequest {
    private String id_position;
    private String name_position;
    private String describe;
    private boolean status;

    public ResponseEntity<?> validate() {
        if (Strings.isNullOrEmpty(id_position)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã chức vụ"));
        }
        if (Strings.isNullOrEmpty(name_position)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên chức vụ"));
        }
        if (Strings.isNullOrEmpty(describe)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        return null;
    }
}
