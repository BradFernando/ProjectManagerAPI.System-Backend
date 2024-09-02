package com.system.pma.controller;

import com.system.pma.dto.ReportRequestDto;
import com.system.pma.dto.ReportResponseDto;
import com.system.pma.dto.WorkReportDto;
import com.system.pma.model.WorkReportFile;
import com.system.pma.service.WorkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reportes-trabajo")
@CrossOrigin(origins = "*")
public class WorkReportController {

    private final WorkReportService workReportService;

    @Autowired
    public WorkReportController(WorkReportService workReportService) {
        this.workReportService = workReportService;
    }

    @GetMapping
    public List<WorkReportDto> getAllWorkReports() {
        return workReportService.getAllWorkReports();
    }

    @GetMapping("/{id}")
    public WorkReportDto getWorkReportById(@PathVariable Long id) {
        return workReportService.getWorkReportById(id);
    }

    @PostMapping
    public WorkReportDto createWorkReport(@RequestBody WorkReportDto workReportDto) {
        return workReportService.saveWorkReport(workReportDto);
    }

    @PutMapping("/{id}")
    public WorkReportDto updateWorkReport(@PathVariable Long id, @RequestBody WorkReportDto workReportDto) {
        return workReportService.updateWorkReport(id, workReportDto);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkReport(@PathVariable Long id) {
        workReportService.deleteWorkReport(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ReportResponseDto> getReportsByEmployeeId(@PathVariable Long employeeId) {
        return workReportService.getReportsByEmployeeId(employeeId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable Long fileId) {
        WorkReportFile file = workReportService.getReportFileById(fileId); // Asegúrate de implementar este método
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(file.getPdfFile())));
    }

    @PostMapping("/send-report")
    public ResponseEntity<String> sendReport(
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("reportId") Long reportId) {
        try {
            // Aquí puedes guardar varios archivos relacionados con un mismo reportId.
            ReportRequestDto reportRequestDto = new ReportRequestDto();
            reportRequestDto.setPdf(pdf);
            reportRequestDto.setEmployeeId(employeeId);
            reportRequestDto.setReportId(reportId);

            System.out.println("Recibiendo archivo PDF: " + pdf.getOriginalFilename());
            System.out.println("Employee ID: " + employeeId);
            System.out.println("Report ID: " + reportId);

            // Aquí no es necesario verificar la unicidad de reportId, se pueden asociar varios archivos a un mismo reportId
            workReportService.sendReport(reportRequestDto);

            return ResponseEntity.ok().header("Content-Type", "application/json").body("{\"message\":\"Reporte enviado correctamente.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al enviar el reporte: " + e.getMessage());
        }
    }
}
