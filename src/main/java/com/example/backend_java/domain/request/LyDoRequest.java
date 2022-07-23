package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ErrResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class LyDoRequest {

    private String reason;
    private Integer id_father_reason;
    private Integer salary;
    private boolean status;

    public ResponseEntity<?> validate() {
        if(id_father_reason == null || id_father_reason < 0){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lý do cha"));
        }
        if(Strings.isNullOrEmpty(reason)){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lý do"));
        }
        if(salary == null || salary < 0){
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập hướng lương"));
        }
        return null;
    }
}
