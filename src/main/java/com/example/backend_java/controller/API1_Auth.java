package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.LoginRequest;
import com.example.backend_java.domain.request.LogoutRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "API_1", description = "Đăng Nhập,Đăng xuất", tags = {"API_1"})
public class API1_Auth extends _BaseController {

    private final AuthService authService;

    public API1_Auth(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/login")
    @ApiOperation(value = "Đăng nhập")
    public ResponseEntity<?> login(HttpServletResponse request,
           @ApiParam(value = "Mật khẩu password được mã hóa: md5(password)= 5f4dcc3b5aa765d61d8327deb882cf99")
           @RequestBody LoginRequest login) {
        logger.info("=>login req: {}",login);
        if (login == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            ResponseEntity<?> response = login.validate();
            if (response == null) {
                logger.info("<=login  resp: {}", "success");
               return authService.login(request,login);
            }
            return response;
        }
    }
    @PostMapping("/api/logout")
    @ApiOperation(value = "Đăng xuất")
    public ResponseEntity<?> logout(@ApiParam(value = "Đăng xuất theo token")
                                    @RequestBody LogoutRequest logout) {
        logger.info("=>login req: {}",logout);
        if (logout == null) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Vui lòng nhập đầy đủ thông tin"));
        } else {
            ResponseEntity<?> response = logout.validate();
            if (response == null) {
                logger.info("<=logout  resp: {}", "success");
                return authService.logout(logout);
            }
            return response;
        }
    }
}
