package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.LyDoRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.LyDoRepository;
import com.example.backend_java.service.LyDoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/reason")
@Api(value = "API_6", description = "Lý do xin nghỉ", tags = {"API_6"})
public class API6_LyDo extends _BaseController {

    private final LyDoRepository lyDoRepository;
    private final LyDoService lyDoService;

    public API6_LyDo(LyDoRepository lyDoRepository, LyDoService lyDoService) {
        this.lyDoRepository = lyDoRepository;
        this.lyDoService = lyDoService;
    }

    @GetMapping("getCha")
    @ApiOperation(value = "Danh sách lý do theo id cha")
    public ResponseEntity<?> listCha(
            @RequestParam(name = "id_cha") @ApiParam(value = "Nhập trạng thái") Integer id_cha) {
        return lyDoService.listCha(id_cha);
    }

    @GetMapping("/page")
    @ApiOperation(value = "Danh sách lý do phân trang")
    public ResponseEntity<?> list(
            @RequestParam(required=false,name = "page_index") @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
            @RequestParam(required=false,name ="page_size") @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
            @RequestParam(required = false, name = "lyDo") @ApiParam(value = "Nhập tên lý do") String lyDo,
            @RequestParam(required = false, name = "idCha") @ApiParam(value = "Nhập id cha của lý do") Integer idCha) {
        Integer index =  validPageIndex(pageIndex);
        Integer size =  validPageSize(pageSize);
        return lyDoService.getPage(index,size,lyDo,idCha);
    }

    @PostMapping("")
    @ApiOperation(value = "Thêm lý do")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody LyDoRequest reason) {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Department invalid"));
        } else {
            ResponseEntity<?> response = reason.validate();
            if (response == null) {
                logger.info("<=create Lý do xin nghỉ resp: {}","ok");
                return  lyDoService.createLyDo(request,reason);
            }
            return response;
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Sửa lý do")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody LyDoRequest reason,
                                    @PathVariable(value = "id") Long id){
        logger.info("=> update Lý do xin nghỉ");
        if(request == null || id < 0 ) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            ResponseEntity<?> response = reason.validate();
            if(response == null ) {
                logger.info("<=update Lý do xin nghỉ resp: {}","ok");
                return lyDoService.updateLyDo(request,reason,id) ;
            }
            return response;
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa lý do")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete Lý do xin nghỉ");
        if( id < 0 ) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        } else {
            return lyDoService.deleteLyDo(id) ;
        }
    }
}
