package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserLoginDto {
    private Object id;
    private Object id_user;
    private Object full_name;
    private Object sex;
    private Object birthday;
    private Object phone;
    private Object email;
    private Object avatar;
    private Object address;
    private Object cmt;
    private Object name_department;
    private Object name_position;
    private Object role;

    public UserLoginDto(Object id, Object id_user, Object full_name, Object sex, Object birthday, Object phone, Object email, Object avatar, Object address, Object cmt, Object name_department, Object name_position, Object role) {
        this.id = id;
        this.id_user = id_user;
        this.full_name = full_name;
        this.sex = sex;
        this.birthday = TimeUtil.toDDMMyyyy((Timestamp)birthday);
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.cmt = cmt;
        this.name_department = name_department;
        this.name_position = name_position;
        this.role = role;
    }
}
