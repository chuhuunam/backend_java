package com.example.backend_java.controller;

import com.example.backend_java.service.LoaiHopDongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/type_contract")
@Api(value = "API_7", description = "Loại hợp đồng", tags = {"API_7"})
public class API7_LoaiHopDong extends _BaseController {

    private final LoaiHopDongService loaiHopDongService;

    public API7_LoaiHopDong(LoaiHopDongService loaiHopDongService) {
        this.loaiHopDongService = loaiHopDongService;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Danh sách loại hợp đồng")
    public ResponseEntity<?> list() {
        return loaiHopDongService.getAll();
    }

    @GetMapping("/page")
    @ApiOperation(value = "Danh sách loại hợp đồng phân trang")
    public ResponseEntity<?> list(
            @RequestParam(required = false, name = "page_index")
            @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
            @RequestParam(required = false, name = "page_size")
            @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
            @RequestParam(required = false, name = "contract_name")
            @ApiParam(value = "Nhập tên loại hợp đồng ") String contract_name,
            @RequestParam(required = false, name = "insurance")
            @ApiParam(value = "Nhập bảo hiểm ") Integer insurance) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return loaiHopDongService.getPage(index, size, contract_name, insurance);
    }

//    @PostMapping("")
//    @ApiOperation(value = "Thêm loại hợp đồng")
//    public ResponseEntity<?> create(HttpServletRequest request,
//                                    @RequestBody LoaiHopDongRequest req) {
//        if (request == null) {
//            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
//        } else {
//            ResponseEntity<?> response = req.validate();
//            if (response == null) {
//                logger.info("<=create loại hợp đồng resp: {}", "ok");
//                return loaiHopDongService.createTypeContract(request, req);
//            }
//            return response;
//        }
//    }
//
//    @PutMapping("{id}")
//    @ApiOperation(value = "Sửa loại hợp đồng")
//    public ResponseEntity<?> update(HttpServletRequest request,
//                                    @RequestBody LoaiHopDongRequest req,
//                                    @PathVariable(value = "id") Long id) {
//        logger.info("=> update loại hợp đồng");
//        if (request == null) {
//            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
//        } else {
//            ResponseEntity<?> response = req.validate();
//            if (response == null) {
//                logger.info("<=update loại hợp đồng resp: {}", "ok");
//                return loaiHopDongService.updateTypeContract(request, req, id);
//            }
//            return response;
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Xóa loại hợp đồng")
//    public ResponseEntity<?> delete(
//            @PathVariable(value = "id") Long id) {
//        logger.info("=>delete loại hợp đồng");
//        if (id == null) {
//            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
//        } else {
//            return loaiHopDongService.deleteTypeContract(id);
//        }
//    }
}
