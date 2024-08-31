package com.system.pma.controller;

import com.system.pma.dto.JobTypeDto;
import com.system.pma.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-trabajo")
@CrossOrigin(origins = "*")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @Autowired
    public JobTypeController(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    @GetMapping
    public List<JobTypeDto> getAllJobTypes() {
        return jobTypeService.getAllJobTypes();
    }

    @GetMapping("/{id}")
    public JobTypeDto getJobTypeById(@PathVariable Long id) {
        return jobTypeService.getJobTypeById(id);
    }

    @PostMapping
    public JobTypeDto createJobType(@RequestBody JobTypeDto jobTypeDto) {
        return jobTypeService.saveJobType(jobTypeDto);
    }

    @PutMapping("/{id}")
    public JobTypeDto updateJobType(@PathVariable Long id, @RequestBody JobTypeDto jobTypeDto) {
        return jobTypeService.updateJobType(id, jobTypeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteJobType(@PathVariable Long id) {
        jobTypeService.deleteJobType(id);
    }
}
