package com.example.backend_java.domain.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserRequest {
    private String name_user;
    private String sex;
    private Date birthday;
    private String phone;
    private String email;
    private String avatar;
    private String address;
    private String cmt;
    private boolean status;
    private Set<String> roles;
    private Integer id_department;
    private Integer id_position;
}
