package com.system.pma.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportRequestDto {
    private MultipartFile pdf; // Archivo PDF enviado desde Angular
    private Long employeeId;   // ID del empleado al que se le debe enviar el PDF
    private Long reportId;     // ID del reporte de trabajo asociado
}
