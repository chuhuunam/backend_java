package com.example.backend_java.controller;

import com.example.backend_java.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Danh sách người dùng phân trang")
    public ResponseEntity<?> list(HttpServletRequest request,
                                  @RequestParam(required = false, name = "page_index")
            @ApiParam(value = "Trang cần lấy (tính từ 1). Mặc định 1") Integer pageIndex,
                                  @RequestParam(required = false, name = "page_size")
            @ApiParam(value = "Số bản ghi cho 1 trang (nhỏ nhất 1; lớn nhất 50). Mặc định 10") Integer pageSize) {
        Integer index = validPageIndex(pageIndex);
        Integer size = validPageSize(pageSize);
        return userService.getPageUser(request,index, size);
    }
}
