package com.system.pma.dto;

import lombok.Data;

@Data
public class ReportResponseDto {
    private String downloadUrl; // URL de descarga del PDF
    private Long employeeId;    // ID del empleado al que se le debe enviar el PDF
    private Long reportId;      // ID del reporte de trabajo asociado
}
