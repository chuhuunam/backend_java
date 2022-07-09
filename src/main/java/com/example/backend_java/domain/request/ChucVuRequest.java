package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ChucVuRequest {
    private String maChucVu;

    private String tenChucVu;

    private String moTa;
    private boolean status;
    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(maChucVu)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã chức vụ"));
        }
        if(Strings.isNullOrEmpty(tenChucVu)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên chức vụ"));
        }
        if(Strings.isNullOrEmpty(moTa)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        return null;
    }
}
