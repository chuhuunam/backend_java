package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserLeaveDto {
    private Object id;
    private Object id_user;
    private Object name_user;
    private Object name_department;
    private Object name_position;
    private Object day_leave;
    private Object reason_leave;
    private Object labor_nature;

    public UserLeaveDto(Object id,Object id_user, Object name_user, Object name_department, Object name_position, Object day_leave, Object reason_leave, Object labor_nature) {
        this.id = id;
        this.id_user = id_user;
        this.name_user = name_user;
        this.name_department = name_department;
        this.name_position = name_position;
        this.day_leave = TimeUtil.toDDMMyyyy((Timestamp) day_leave);
        this.reason_leave = reason_leave;
        this.labor_nature = labor_nature;
    }
}
