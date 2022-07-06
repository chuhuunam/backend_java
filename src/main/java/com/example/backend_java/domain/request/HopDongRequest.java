package com.example.backend_java.domain.request;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.response.ResponseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Data
public class HopDongRequest {
    private Long id_user;
    private Integer id_loai_hop_dong;
    private Integer id_chuc_vu;
    private Float luong;
    private Date ngayKy;
    private Date ngayHieuLuc;
    private Date ngayKetThuc;
    @ApiModelProperty(notes = "Status", example = "1")
    private boolean status;
    private String moTa;

    public ResponseEntity<?> validate() {
        if(id_user < 0 && id_user == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_user "));
        }
        if(id_loai_hop_dong < 0 && id_loai_hop_dong == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập id_loai_hop_dong "));
        }
        if(luong < 0 && luong == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập lương "));
        }
        if(ngayHieuLuc == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày hiệu lực "));
        }
        if(ngayKetThuc == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày kết thúc "));
        }
        if(ngayKy == null){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập ngày ký "));
        }
        return null;
    }
}
