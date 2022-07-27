package com.example.backend_java.domain.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PieChartDto {
    private BigInteger count;
    private Object title;

    public PieChartDto(BigInteger count, Object title) {
        this.count = count;
        this.title = title;
    }
}
