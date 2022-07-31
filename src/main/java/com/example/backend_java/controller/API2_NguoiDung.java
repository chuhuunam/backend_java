package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.*;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.service.UserService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@Api(value = "API_2", description = "Nhân viên", tags = {"API_2"})
public class API2_NguoiDung extends _BaseController {

    private final UserService userService;

    public API2_NguoiDung(UserService userService) {
        this.userService = userService;
    }

    //    @GetMapping("/fake")
//    public void a(HttpServletRequest request) throws NoSuchAlgorithmException {
//        userService.s(request);
//    }
    @PostMapping("/leave/{id}")
    @ApiOperation(value = "Cho nhân viên nghỉ việc")
    public ResponseEntity<?> quit_job(HttpServletRequest request,
                                      @RequestBody NghiViecRequest nghiViecRequest, @PathVariable(value = "id") Long id){
        if (request == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            logger.info("<=create nhân viên resp: {}", "ok");
            return userService.quit_job(request, nghiViecRequest,id);
        }
    }


    @GetMapping("/statistical")
    @ApiOperation(value = "Thống kê nhân viên")
    public ResponseEntity<?> statistical() {
        return userService.statistical();
    }

    @GetMapping("leave/page")
    @ApiOperation(value = "Danh sách nhân viên nghỉ việc phân trang")
    public ResponseEntity<?> leave(
                                  @RequestParam(required = false, name = "page_index")
                                  @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
                                  @RequestParam(required = false, name = "page_size")
                                  @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
                                  @RequestParam(required = false, name = "keyword")
                                  @ApiParam(value = "Nhập mã nhân viên, tên, cmt,...") String keyword,
                                  @RequestParam(required = false, name = "id_department")
                                  @ApiParam(value = "Nhập id_phong_ban") Integer id_department,
                                  @RequestParam(required = false, name = "id_position")
                                  @ApiParam(value = "Nhập idChucVu") Integer id_position){
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return userService.getUserLeave(keyword, id_department, id_position, index, size);
    }

    @GetMapping("page")
    @ApiOperation(value = "Danh sách nhân viên phân trang")
    public ResponseEntity<?> list(HttpServletRequest request,
                                  @RequestParam(required = false, name = "page_index")
                                  @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
                                  @RequestParam(required = false, name = "page_size")
                                  @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
                                  @RequestParam(required = false, name = "keyword")
                                  @ApiParam(value = "Nhập mã nhân viên, tên, cmt,...") String keyword,
                                  @RequestParam(required = false, name = "id_department")
                                  @ApiParam(value = "Nhập id_phong_ban") Integer id_department,
                                  @RequestParam(required = false, name = "id_position")
                                  @ApiParam(value = "Nhập idChucVu") Integer id_position,
                                  @RequestParam(required = false, name = "id_type_contract")
                                  @ApiParam(value = "Nhập idLoaiHopDong") Integer id_type_contract,
                                  @RequestParam(required = false, name = "sex")
                                  @ApiParam(value = "Nhập giới tính") String sex) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return userService.getPageUser(request, keyword, id_department, id_position, id_type_contract, sex, index, size);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Chi tiết nhân viên")
    public ResponseEntity<?> listUserId(@PathVariable(value = "id") Long id) {
        if (id == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        }
        return userService.getUser(id);
    }

    @GetMapping("/vetifytoken")
    @ApiOperation(value = "User đăng nhập")
    public ResponseEntity<?> vetifytoken(HttpServletRequest request) {
        return userService.getList(request);
    }


    @PostMapping("")
    @ApiOperation(value = "Thêm nhân viên")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @ModelAttribute UserRequest user) throws MessagingException, IOException {
        if (request == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            logger.info("<=create nhân viên resp: {}", "ok");
            return userService.createUser(request, user);
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Sửa nhân viên")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @ModelAttribute UserRequest user,
                                    @PathVariable(value = "id") Long id) throws IOException {
        logger.info("=> update nhân viên");
        if (request == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            logger.info("<=update nhân viên resp: {}", "ok");
            return userService.updateUser(request, user, id);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa nhân viên")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete nhân viên");
        if (id == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            return userService.deleteUser(id);
        }
    }

    @PutMapping("updatePassword/{id}")
    @ApiOperation(value = "Sửa tài khoản người dùng")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            @RequestBody PasswordRequest passwordRequest,
                                            @PathVariable(value = "id") Long id) {
        logger.info("=> update pass người dùng");
        if (request == null || id < 0) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        }
        if (Strings.isNullOrEmpty(passwordRequest.getPassword())) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Bạn chưa nhập mật khẩu"));
        } else {
            return userService.updatePassword(request, passwordRequest, id);
        }
    }

    @GetMapping("/export_file")
    @ApiOperation(value = "Xuất file")
    public void exportFile(HttpServletResponse response) throws IOException {
        userService.exportFile(response);
    }
}
