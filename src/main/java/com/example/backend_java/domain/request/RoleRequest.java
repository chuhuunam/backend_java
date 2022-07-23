package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class RoleRequest {
    private String id_role;
    private String name_role;
    private String describe;
    private boolean status;

    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(id_role)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã quyền"));
        }
        if(Strings.isNullOrEmpty(name_role)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên quyền"));
        }
        if(Strings.isNullOrEmpty(describe)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        return null;
    }
}
