package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class LoaiHopDongRequest {
    private String tenHopDong;
    @ApiModelProperty(notes = "Bảo hiểm", example = "0 là không,1 là có")
    private Integer baoHiem;
    private boolean status;
    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(tenHopDong)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên hợp đồng"));
        }
        if(baoHiem < 0 && baoHiem == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập BHXH "));
        }
        return null;
    }
}
