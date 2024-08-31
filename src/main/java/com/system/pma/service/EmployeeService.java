package com.system.pma.service;

import com.system.pma.dto.EmployeeDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Area;
import com.system.pma.model.Employee;
import com.system.pma.model.JobType;
import com.system.pma.repository.AreaRepository;
import com.system.pma.repository.EmployeeRepository;
import com.system.pma.repository.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AreaRepository areaRepository;
    private final JobTypeRepository jobTypeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AreaRepository areaRepository, JobTypeRepository jobTypeRepository) {
        this.employeeRepository = employeeRepository;
        this.areaRepository = areaRepository;
        this.jobTypeRepository = jobTypeRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + id));
        return convertToDto(employee);
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setCI(employeeDto.getCI());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Area area = areaRepository.findById(employeeDto.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con el ID: " + employeeDto.getAreaId()));
        employee.setArea(area);
        JobType jobType = jobTypeRepository.findById(employeeDto.getJobTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de trabajo no encontrado con el ID: " + employeeDto.getJobTypeId()));
        employee.setJobType(jobType);
        return convertToDto(employeeRepository.save(employee));
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + id));
        employee.setCI(employeeDto.getCI());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Area area = areaRepository.findById(employeeDto.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Área no encontrada con el ID: " + employeeDto.getAreaId()));
        employee.setArea(area);
        JobType jobType = jobTypeRepository.findById(employeeDto.getJobTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de trabajo no encontrado con el ID: " + employeeDto.getJobTypeId()));
        employee.setJobType(jobType);
        return convertToDto(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + id));
        employeeRepository.delete(employee);
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setCI(employee.getCI());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setAreaId(employee.getArea().getId());
        employeeDto.setJobTypeId(employee.getJobType().getId());
        return employeeDto;
    }

    public EmployeeDto getEmployeeByCI(String ci) {
        Employee employee = employeeRepository.findByCI(ci)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el CI: " + ci));
        return convertToDto(employee);
    }


}