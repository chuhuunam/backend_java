package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class PhongBanRequest {
    private String maPhongBan;

    private String tenPhongBan;

    private String moTa;
    private boolean status;

    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(maPhongBan)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mã phòng ban"));
        }
        if(Strings.isNullOrEmpty(tenPhongBan)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên phòng ban"));
        }
        if(Strings.isNullOrEmpty(moTa)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập mô tả"));
        }
        return null;
    }

}
