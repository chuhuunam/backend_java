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
    private Object working_day;
    private Object update_at;

    public UserDto(Object id, Object id_user, Object name_user, Object sex, Object birthday, Object phone, Object email, Object avatar, Object address, Object cmt, boolean status, Object name_department, Object name_position, Object labor_nature, Object type_contract, Object insurance, Object working_day, Object update_at) {
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
        this.working_day = TimeUtil.toDDMMyyyy((Timestamp)working_day);
        this.update_at = TimeUtil.toDDMMyyyy((Timestamp)update_at);
    }
}
