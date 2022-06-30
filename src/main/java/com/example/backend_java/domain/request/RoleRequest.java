package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class RoleRequest {
    private String maQuyen;
    private String tenQuyen;
    private String moTa;
    @ApiModelProperty(notes = "Status", example = "1")
    private Integer status;

    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(maQuyen)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã quyền"));
        }
        if(Strings.isNullOrEmpty(tenQuyen)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên quyền"));
        }
        if(Strings.isNullOrEmpty(moTa)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        if (status == null || status < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập Status"));
        }
        return null;
    }
}
