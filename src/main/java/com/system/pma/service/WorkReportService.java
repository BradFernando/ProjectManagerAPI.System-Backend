package com.system.pma.service;

import com.system.pma.dto.WorkReportDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Employee;
import com.system.pma.model.Task;
import com.system.pma.model.WorkReport;
import com.system.pma.repository.EmployeeRepository;
import com.system.pma.repository.TaskRepository;
import com.system.pma.repository.WorkReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkReportService {

    private final WorkReportRepository workReportRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public WorkReportService(WorkReportRepository workReportRepository, TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.workReportRepository = workReportRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
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
}