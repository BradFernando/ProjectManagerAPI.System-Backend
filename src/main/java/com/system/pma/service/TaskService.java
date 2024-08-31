package com.system.pma.service;

import com.system.pma.dto.TaskDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.Employee;
import com.system.pma.model.Task;
import com.system.pma.repository.EmployeeRepository;
import com.system.pma.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + id));
        return convertToDto(task);
    }

    public TaskDto saveTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        Employee employee = employeeRepository.findById(taskDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + taskDto.getEmployeeId()));
        task.setEmployee(employee);
        return convertToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + id));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        Employee employee = employeeRepository.findById(taskDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con el ID: " + taskDto.getEmployeeId()));
        task.setEmployee(employee);
        return convertToDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con el ID: " + id));
        taskRepository.delete(task);
    }

    private TaskDto convertToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setEmployeeId(task.getEmployee().getId());
        return taskDto;
    }
}
