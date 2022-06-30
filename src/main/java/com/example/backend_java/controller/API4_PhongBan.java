package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.PhongBanRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.PhongBanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/department")
@Api(value = "API_4", description = "Phòng ban", tags = {"API_4"})
public class API4_PhongBan extends _BaseController {

    private final PhongBanService phongBanService;

    public API4_PhongBan(PhongBanService phongBanService) {
        this.phongBanService = phongBanService;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Danh sách phòng ban")
    public ResponseEntity<?> list() {
        return phongBanService.getAll();
    }

    @GetMapping("/page")
    @ApiOperation(value = "Danh sách phòng ban phân trang")
    public ResponseEntity<?> list(
            @RequestParam(required = false, name = "page_index")
            @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
            @RequestParam(required = false, name = "page_size")
            @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
            @RequestParam(required = false, name = "name")
            @ApiParam(value = "Nhập tên phòng ban") String tenPhongBan) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return phongBanService.getPage(index, size,tenPhongBan);
    }

    @PostMapping("")
    @ApiOperation(value = "Thêm phòng ban")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody PhongBanRequest department) {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Department invalid"));
        } else {
            ResponseEntity<?> response = department.validate();
            if (response == null) {
                logger.info("<=create phòng ban resp: {}", "ok");
                return phongBanService.createDepartment(request, department);
            }
            return response;
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Sửa phòng ban")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody PhongBanRequest department,
                                    @PathVariable(value = "id") Long id) {
        logger.info("=> update phòng ban");
        if (request == null || id < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            ResponseEntity<?> response = department.validate();
            if (response == null) {
                logger.info("<=update resp: {}", "ok");
                return phongBanService.updateDepartment(request, department, id);
            }
            return response;
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa phòng ban")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete phòng ban");
        if (id < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            return phongBanService.deleteDepartment(id);
        }
    }
}
