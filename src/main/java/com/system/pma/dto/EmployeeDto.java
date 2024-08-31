package com.system.pma.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String CI;
    private String firstName;
    private String lastName;
    private String email;
    private Long areaId;
    private Long jobTypeId;
}