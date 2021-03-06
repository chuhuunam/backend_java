package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class LoaiHopDongRequest {
    private String name_contract;
    @ApiModelProperty(notes = "Bảo hiểm", example = "0 là không,1 là có")
    private Integer insurance;
    private boolean status;
    @ApiModelProperty(notes = "tính chất lao động", example = "Chính thức, Thử việc, Học việc")
    private String labor_nature;

    public ResponseEntity<?> validate() {
        if (Strings.isNullOrEmpty(name_contract)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên hợp đồng"));
        }
        if (insurance < 0 && insurance == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập BHXH "));
        }
        if (Strings.isNullOrEmpty(labor_nature)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tính chất lao động"));
        }
        return null;
    }
}
