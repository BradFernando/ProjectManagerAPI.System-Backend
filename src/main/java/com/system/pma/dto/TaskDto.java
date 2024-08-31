package com.system.pma.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long employeeId;
    private String status;
}
