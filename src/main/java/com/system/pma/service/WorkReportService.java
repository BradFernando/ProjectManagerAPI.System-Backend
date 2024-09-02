package com.system.pma.service;

import com.system.pma.dto.ReportRequestDto;
import com.system.pma.dto.ReportResponseDto;
import com.system.pma.dto.WorkReportDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Employee;
import com.system.pma.model.Task;
import com.system.pma.model.WorkReport;
import com.system.pma.model.WorkReportFile;
import com.system.pma.repository.EmployeeRepository;
import com.system.pma.repository.TaskRepository;
import com.system.pma.repository.WorkReportFileRepository;
import com.system.pma.repository.WorkReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkReportFileRepository workReportFileRepository;


    @Autowired
    public WorkReportService(WorkReportRepository workReportRepository, TaskRepository taskRepository, EmployeeRepository employeeRepository, WorkReportFileRepository workReportFileRepository) {
        this.workReportRepository = workReportRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.workReportFileRepository = workReportFileRepository;
    }

    public List<WorkReportDto> getAllWorkReports() {
        return workReportRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public WorkReportDto getWorkReportById(Long id) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte de trabajo no encontrado con el ID: " + id));
        return convertToDto(workReport);
    }

    public WorkReportDto saveWorkReport(WorkReportDto workReportDto) {
        WorkReport workReport = new WorkReport();
        workReport.setDescription(workReportDto.getDescription());
        workReport.setHoursWorked(workReportDto.getHoursWorked());
        Task task = taskRepository.findById(workReportDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + workReportDto.getTaskId()));
        Employee employee = employeeRepository.findById(workReportDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + workReportDto.getEmployeeId()));
        workReport.setTask(task);
        workReport.setEmployee(employee);
        return convertToDto(workReportRepository.save(workReport));
    }

    public WorkReportDto updateWorkReport(Long id, WorkReportDto workReportDto) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte de trabajo no encontrado con el ID: " + id));
        workReport.setDescription(workReportDto.getDescription());
        workReport.setHoursWorked(workReportDto.getHoursWorked());
        Task task = taskRepository.findById(workReportDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + workReportDto.getTaskId()));
        Employee employee = employeeRepository.findById(workReportDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + workReportDto.getEmployeeId()));
        workReport.setTask(task);
        workReport.setEmployee(employee);
        return convertToDto(workReportRepository.save(workReport));
    }

    public void deleteWorkReport(Long id) {
        WorkReport workReport = workReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte de trabajo no encontrado con el ID: " + id));
        workReportRepository.delete(workReport);
    }

    private WorkReportDto convertToDto(WorkReport workReport) {
        WorkReportDto workReportDto = new WorkReportDto();
        workReportDto.setId(workReport.getId());
        workReportDto.setDescription(workReport.getDescription());
        workReportDto.setHoursWorked(workReport.getHoursWorked());
        workReportDto.setTaskId(workReport.getTask().getId());
        workReportDto.setEmployeeId(workReport.getEmployee().getId());
        return workReportDto;
    }



    @Transactional(readOnly = true)
    public List<ReportResponseDto> getReportsByEmployeeId(Long employeeId) {
        List<WorkReportFile> workReportFiles = workReportFileRepository.findByWorkReportEmployeeId(employeeId);

        return workReportFiles.stream().map(file -> {
            ReportResponseDto dto = new ReportResponseDto();
            dto.setEmployeeId(file.getWorkReport().getEmployee().getId());
            dto.setReportId(file.getWorkReport().getId());
            // Construir la URL de descarga para el archivo PDF
            dto.setDownloadUrl("/api/reportes-trabajo/download/" + file.getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WorkReportFile getReportFileById(Long fileId) {
        return workReportFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Archivo no encontrado con el ID: " + fileId));
    }

    public void sendReport(ReportRequestDto reportRequestDto) throws IOException {
        Optional<WorkReport> workReportOptional = workReportRepository.findById(reportRequestDto.getReportId());

        if (workReportOptional.isPresent()) {
            WorkReport workReport = workReportOptional.get();
            byte[] pdfBytes = reportRequestDto.getPdf().getBytes();

            WorkReportFile workReportFile = new WorkReportFile();
            workReportFile.setPdfFile(pdfBytes);
            workReportFile.setWorkReport(workReport);
            workReportFile.setFileName(reportRequestDto.getPdf().getOriginalFilename());
            workReportFile.setMimeType(reportRequestDto.getPdf().getContentType());

            workReportFileRepository.save(workReportFile);

            // Aquí puedes enviar una notificación al usuario correspondiente
            // sendNotificationToEmployee(employee);
        } else {
            throw new RuntimeException("Reporte de trabajo no encontrado con ID: " + reportRequestDto.getReportId());
        }
    }
}