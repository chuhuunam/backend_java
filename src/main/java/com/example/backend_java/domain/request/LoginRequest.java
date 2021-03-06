package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class LoginRequest {
    @ApiModelProperty(notes = "Tài khoản", example = "namch")
    private String username;
    @ApiModelProperty(notes = "Mật Khẩu", example = "5f4dcc3b5aa765d61d8327deb882cf99")
    private String password;

    public ResponseEntity<?> validate() {
        if (Strings.isNullOrEmpty(username)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập username"));
        }
        if (Strings.isNullOrEmpty(password)) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập password"));
        }
        return null;
    }

}
