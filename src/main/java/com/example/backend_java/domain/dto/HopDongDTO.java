package com.example.backend_java.domain.dto;

import com.example.backend_java.utils.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class HopDongDTO {
    private Object id;
    private Object full_name;
    private Object name_contract;
    private Object id_contract;
    private Object id_position;
    private Object name_department;
    private Object sign_day;
    private Object effective_date;
    private Object end_date;
    private Float salary;
    private Object duration;
    private boolean status;
    private Object describe;
    private Integer insurance;

    public HopDongDTO(Object id, Object full_name, Object name_contract, Object id_contract, Object id_position, Object name_department, Object sign_day, Object effective_date, Object end_date, Float salary, Object duration, boolean status, Object describe, Integer insurance) {
        this.id = id;
        this.full_name = full_name;
        this.name_contract = name_contract;
        this.id_contract = id_contract;
        this.id_position = id_position;
        this.name_department = name_department;
        this.sign_day = TimeUtil.toDDMMyyyy((Timestamp)sign_day);
        this.effective_date = TimeUtil.toDDMMyyyy((Timestamp)effective_date);
        this.end_date = TimeUtil.toDDMMyyyy((Timestamp)end_date);
        this.salary = salary;
        this.duration = duration;
        this.status = status;
        this.describe = describe;
        this.insurance = insurance;
    }
}
