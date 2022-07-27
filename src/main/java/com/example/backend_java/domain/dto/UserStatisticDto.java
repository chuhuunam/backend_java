package com.example.backend_java.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserStatisticDto {
    private Integer total;
    private List<PieChartDto> pieChart;
    private List<ContractChartDto> contractChart;
}
