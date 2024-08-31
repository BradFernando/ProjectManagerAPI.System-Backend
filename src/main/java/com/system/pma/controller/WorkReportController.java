package com.system.pma.controller;

import com.system.pma.dto.WorkReportDto;
import com.system.pma.service.WorkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
