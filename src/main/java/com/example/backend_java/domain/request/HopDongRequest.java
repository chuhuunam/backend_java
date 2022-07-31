package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Data
public class HopDongRequest {
    private Long id_user;
    private Integer id_type_contract;
    private Integer id_position;
    private Float salary;
    private Date sign_day;
    private Date effective_date;
    private Date end_date;
    private boolean status;
    private String describe;

    public ResponseEntity<?> validate() {
        if (id_user < 0 && id_user == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_user "));
        }
        if (id_type_contract < 0 && id_type_contract == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_loai_hop_dong "));
        }
        if (salary < 0 && salary == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lương "));
        }
        if (effective_date == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày hiệu lực "));
        }
        if (end_date == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày kết thúc "));
        }
        if (sign_day == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày ký "));
        }
        return null;
    }
}
