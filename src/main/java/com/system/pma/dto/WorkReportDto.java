package com.system.pma.dto;

import lombok.Data;

@Data
public class WorkReportDto {
    private Long id;
    private String description;
    private Long taskId;
    private Long employeeId;
    private int hoursWorked;
}