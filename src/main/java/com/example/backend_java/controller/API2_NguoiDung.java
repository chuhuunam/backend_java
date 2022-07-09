package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.PasswordRequest;
import com.example.backend_java.domain.request.StatusRequest;
import com.example.backend_java.domain.request.UserRequest;
import com.example.backend_java.domain.request.updateDepRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.UserService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@Api(value = "API_2", description = "Nhân viên", tags = {"API_2"})
public class API2_NguoiDung extends _BaseController{

    private final UserService userService;

    public API2_NguoiDung(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    @ApiOperation(value = "Danh sách nhân viên phân trang")
    public ResponseEntity<?> list(HttpServletRequest request,
                                  @RequestParam(required = false, name = "page_index")
            @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
                                  @RequestParam(required = false, name = "page_size")
            @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize,
                                  @RequestParam(required = false, name = "keyword")
                                      @ApiParam(value = "Nhập mã nhân viên, tên, cmt,...") String keyword,
                                  @RequestParam(required = false, name = "id_phong_ban")
                                      @ApiParam(value = "Nhập id_phong_ban") Integer idPhongBan,
                                  @RequestParam(required = false, name = "idChucVu")
                                      @ApiParam(value = "Nhập idChucVu") Integer idChucVu) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return userService.getPageUser(request,keyword,idPhongBan,idChucVu,index, size);
    }
    @PostMapping("")
    @ApiOperation(value = "Thêm nhân viên")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody UserRequest user) throws MessagingException {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Role invalid"));
        } else {
            logger.info("<=create nhân viên resp: {}", "ok");
            return userService.createUser(request, user);
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Sửa nhân viên")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody UserRequest user,
                                    @PathVariable(value = "id") Long id) {
        logger.info("=> update nhân viên");
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Role invalid"));
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
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Role invalid"));
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
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        }if(Strings.isNullOrEmpty(passwordRequest.getMat_khau())){
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Chưa nhập password"));
        }else {
            return userService.updatePassword(request, passwordRequest, id);
        }
    }
    @PutMapping("updateStatus/{id}")
    @ApiOperation(value = "Sửa trạng thái người dùng")
    public ResponseEntity<?> updateStatus(HttpServletRequest request,
                                          @RequestBody StatusRequest status,
                                          @PathVariable(value = "id") Long id) {
        logger.info("=> update pass người dùng");
        if (request == null || id < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        }else {
            return userService.updateStatus(request, status, id);
        }
    }

    @PutMapping("updateDepartment/{id}")
    @ApiOperation(value = "Chuyển phòng ban người dùng")
    public ResponseEntity<?> updateDepartment(HttpServletRequest request,
                                              @RequestBody updateDepRequest department,
                                              @PathVariable(value = "id") Long id) {
        logger.info("=> update pass người dùng");
        if (request == null || id < 0) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Department invalid"));
        }else {
            return userService.updateDepartment(request, department, id);
        }
    }
}
