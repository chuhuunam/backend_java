package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.ChucVuRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.ChucVuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/position")
@Api(value = "API_5", description = "Chức vụ", tags = {"API_5"})
public class API5_ChucVu extends _BaseController{

    private final ChucVuService chucVuService;

    public API5_ChucVu(ChucVuService chucVuService) {
        this.chucVuService = chucVuService;
    }
    @GetMapping("getAll")
    @ApiOperation(value = "Danh sách chức vụ")
    public ResponseEntity<?> listAll() {
        return chucVuService.getAll();
    }

    @GetMapping("/page")
    @ApiOperation(value = "Danh sách chức vụ phân trang")
    public ResponseEntity<?> list(
        @RequestParam(required=false,name = "page_index") @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
        @RequestParam(required=false,name ="page_size") @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
        @RequestParam(required = false, name = "keyword")
        @ApiParam(value = "Nhập tên chức vụ") String keyword) {
        Integer index =  validPageIndex(pageIndex);
        Integer size =  validPageSize(pageSize);
        return chucVuService.getPage(index,size,keyword);
    }

    @PostMapping("")
    @ApiOperation(value = "Thêm chức vụ")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody ChucVuRequest position) {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Department invalid"));
        } else {
            ResponseEntity<?> response = position.validate();
            if (response == null) {
                logger.info("<=create chức vụ resp: {}","ok");
                return  chucVuService.createChucVu(request,position);
            }
            return response;
        }
    }
    @PutMapping("/{id}")
    @ApiOperation(value = "Sửa chức vụ")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody ChucVuRequest position,
                                    @PathVariable(value = "id") Long id){
        logger.info("=> update chức vụ");
        if(request == null || id < 0 ) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            ResponseEntity<?> response = position.validate();
            if(response == null ) {
                logger.info("<=update chức vụ resp: {}","ok");
                return chucVuService.updateChucVu(request,position,id) ;
            }
            return response;
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa chức vụ")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete chức vụ");
        if( id < 0 ) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            return chucVuService.deleteChucVu(id) ;
        }
    }
}
