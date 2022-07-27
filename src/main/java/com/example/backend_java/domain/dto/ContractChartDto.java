package com.example.backend_java.domain.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ContractChartDto {
    private BigInteger count;
    private Object title;

    public ContractChartDto(BigInteger count, Object title) {
        this.count = count;
        this.title = title;
    }
}
