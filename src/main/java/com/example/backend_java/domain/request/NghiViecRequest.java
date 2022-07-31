package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class NghiViecRequest {
    @ApiModelProperty(notes = "check status ", example = "0 là đuổi, 1 là nghỉ hưu")
    private Integer check;
    private String reason_leave;

    public ResponseEntity<?> validate() {
        if (check < 0 && check == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_user "));
        }
        if (Strings.isNullOrEmpty(reason_leave)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lý do nghỉ"));
        }
        return null;
    }
}
