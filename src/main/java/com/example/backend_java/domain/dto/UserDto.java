package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private Object id;
    private Object id_user;
    private Object name_user;
    private Object sex;
    private Object birthday;
    private Object phone;
    private Object email;
    private Object avatar;
    private Object address;
    private Object cmt;
    private boolean status;
    private Object name_department;
    private Object name_position;
    private Object labor_nature;
    private Object type_contract;
    private Object insurance;
    private Object effective_date;
    private Object end_date;

    public UserDto(Object id, Object id_user, Object name_user, Object sex, Object birthday, Object phone, Object email, Object avatar, Object address, Object cmt, boolean status, Object name_department, Object name_position, Object labor_nature, Object type_contract, Object insurance, Object effective_date, Object end_date) {
        this.id = id;
        this.id_user = id_user;
        this.name_user = name_user;
        this.sex = sex;
        this.birthday = TimeUtil.toDDMMyyyy((Timestamp) birthday);
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.cmt = cmt;
        this.status = status;
        this.name_department = name_department;
        this.name_position = name_position;
        this.labor_nature = labor_nature;
        this.type_contract = type_contract;
        this.insurance = insurance;
        this.effective_date = TimeUtil.toDDMMyyyy((Timestamp)effective_date);
        this.end_date = TimeUtil.toDDMMyyyy((Timestamp)end_date);
    }
}
