package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.HopDongRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.HopDongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/contract")
@Api(value = "API_8", description = "Hợp đồng", tags = {"API_8"})
public class API8_HopDong extends _BaseController{

    private final HopDongService hopDongService;

    public API8_HopDong(HopDongService hopDongService) {
        this.hopDongService = hopDongService;
    }
    @GetMapping("/page")
    @ApiOperation(value = "Danh sách hợp đồng phân trang")
    public ResponseEntity<?> list(
            @RequestParam(required = false, name = "page_index")
            @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
            @RequestParam(required = false, name = "page_size")
            @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
            @RequestParam(required = false, name = "tenNhanVien")
            @ApiParam(value = "tên nhân viên") String tenNhanVien,
            @RequestParam(required = false, name = "maLoaiHopDong")
            @ApiParam(value = "mã loại hợp đồng") Integer maLoaiHopDong,
            @RequestParam(required = false, name = "status")
            @ApiParam(value = "0 là bảo hiểm hết hạn,1 ngược lại") Integer status) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return hopDongService.getPage(tenNhanVien,maLoaiHopDong,status,index, size);
    }

    @PostMapping("")
    @ApiOperation(value = "Thêm hợp đồng")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody HopDongRequest hopdong) {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Hợp đồng invalid"));
        } else {
            ResponseEntity<?> response = hopdong.validate();
            if (response == null) {
                logger.info("<=create resp: {}", "ok");
                return hopDongService.createHopDong(request, hopdong);
            }
            return response;
        }
    }
    @PutMapping("{id}")
    @ApiOperation(value = "Sửa hợp đồng")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody HopDongRequest req,
                                    @PathVariable(value = "id") Long id) {
        logger.info("=> update hợp đồng");
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Hợp đồng invalid"));
        } else {
            ResponseEntity<?> response = req.validate();
            if (response == null) {
                logger.info("<=update hợp đồng resp: {}", "ok");
                return hopDongService.updateHopDong(request, req, id);
            }
            return response;
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa hợp đồng")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete hợp đồng");
        if (id == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Hợp đồng invalid"));
        } else {
            return hopDongService.deleteHopDong(id);
        }
    }

    @GetMapping("/export_file")
    @ApiOperation(value = "Xuất file")
    public void exportFile(HttpServletResponse response) throws IOException {
        hopDongService.exportFile(response);
    }
}
