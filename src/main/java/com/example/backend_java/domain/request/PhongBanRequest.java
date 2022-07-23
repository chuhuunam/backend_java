package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class PhongBanRequest {
    private String id_department;
    private String name_department;
    private String describe;
    private boolean status;

    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(id_department)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã phòng ban"));
        }
        if(Strings.isNullOrEmpty(name_department)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên phòng ban"));
        }
        if(Strings.isNullOrEmpty(describe)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        return null;
    }

}
