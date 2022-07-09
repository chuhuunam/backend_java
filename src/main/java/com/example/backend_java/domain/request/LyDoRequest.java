package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class LyDoRequest {

    private Integer id_cha;
    private String lyDo;
    private Integer huongLuong;
    private boolean status;

    public ResponseEntity<?> validate() {
        if(id_cha == null || id_cha < 0){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lý do cha"));
        }
        if(Strings.isNullOrEmpty(lyDo)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lý do"));
        }
        if(huongLuong == null || huongLuong < 0){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập hướng lương"));
        }
        return null;
    }
}
