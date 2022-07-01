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
    @ApiModelProperty(notes = "Loại hợp đồng", example = "Chính thức, Thử việc, Học việc")
    private String loaiHopDong;
    @ApiModelProperty(notes = "Bảo hiểm", example = "0 là không,1 là có")
    private Integer baoHiem;
    @ApiModelProperty(notes = "Status", example = "1")
    private Integer status;
    public ResponseEntity<?> validate() {
        if(Strings.isNullOrEmpty(tenHopDong)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập tên hợp đồng"));
        }
        if(Strings.isNullOrEmpty(loaiHopDong)){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập loại hợp đồng"));
        }
        if(baoHiem < 0 && baoHiem == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập BHXH "));
        }
        if(status < 0 && status == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập status"));
        }
        return null;
    }
}
