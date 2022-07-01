package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class ChucVuRequest {
    private String maChucVu;

    private String tenChucVu;

    private String moTa;
    @ApiModelProperty(notes = "Status", example = "1")
    private Integer status;
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
        if(status < 0 && status == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập status"));
        }
        return null;
    }
}
