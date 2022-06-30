package com.example.backend_java.controller;

import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.request.RoleRequest;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/role")
@Api(value = "API_3", description = "Phân quyền", tags = {"API_3"})
public class API3_Quyen extends _BaseController{

    private final RoleService roleService;

    public API3_Quyen(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Danh sách phân quyền")
    public ResponseEntity<?> list() {
        return roleService.getAll();
    }

    @PostMapping("")
    @ApiOperation(value = "Thêm phân quyền")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody RoleRequest role) {
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Role invalid"));
        } else {
            ResponseEntity<?> response = role.validate();
            if (response == null) {
                logger.info("<=create phân quyền resp: {}", "ok");
                return roleService.createRole(request, role);
            }
            return response;
        }
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Sửa phân quyền")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestBody RoleRequest role,
                                    @PathVariable(value = "id") Long id) {
        logger.info("=> update phân quyền");
        if (request == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Role invalid"));
        } else {
            ResponseEntity<?> response = role.validate();
            if (response == null) {
                logger.info("<=update phân quyền resp: {}", "ok");
                return roleService.updateRole(request, role, id);
            }
            return response;
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Xóa phân quyền")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") Long id) {
        logger.info("=>delete phân quyền");
        if (id == null) {
            return ResponseEntity.ok(new ResponseResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Id Role invalid"));
        } else {
            return roleService.deleteRole(id);
        }
    }
}
