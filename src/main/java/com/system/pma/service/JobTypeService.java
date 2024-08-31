package com.system.pma.service;

import com.system.pma.dto.JobTypeDto;
import com.system.pma.exception.ResourceNotFoundException;
import com.system.pma.model.JobType;
import com.system.pma.repository.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTypeService {

    private final JobTypeRepository jobTypeRepository;

    @Autowired
    public JobTypeService(JobTypeRepository jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;
    }

    public List<JobTypeDto> getAllJobTypes() {
        return jobTypeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public JobTypeDto getJobTypeById(Long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de trabajo no encontrado con el ID: " + id));
        return convertToDto(jobType);
    }

    public JobTypeDto saveJobType(JobTypeDto jobTypeDto) {
        JobType jobType = new JobType();
        jobType.setDescription(jobTypeDto.getDescription());
        return convertToDto(jobTypeRepository.save(jobType));
    }

    public JobTypeDto updateJobType(Long id, JobTypeDto jobTypeDto) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de trabajo no encontrado con el ID: " + id));
        jobType.setDescription(jobTypeDto.getDescription());
        return convertToDto(jobTypeRepository.save(jobType));
    }

    public void deleteJobType(Long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de trabajo no encontrado con el ID: " + id));
        jobTypeRepository.delete(jobType);
    }

    private JobTypeDto convertToDto(JobType jobType) {
        JobTypeDto jobTypeDto = new JobTypeDto();
        jobTypeDto.setId(jobType.getId());
        jobTypeDto.setDescription(jobType.getDescription());
        return jobTypeDto;
    }
}
