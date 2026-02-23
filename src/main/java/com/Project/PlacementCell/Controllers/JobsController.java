package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin("*")
public class JobsController {


    private final JobService jobService;

    public JobsController(JobService jobService) {
        this.jobService = jobService;
    }

    // Admin Add Job
    @PostMapping("/add")
    public JobsDetails addJob(@RequestBody JobDTO dto) {
        return jobService.addJob(dto);
    }

    // User Get Jobs (only not expired)
    @GetMapping("/available")
    public List<JobsDetails> getJobs() {
        return jobService.getAvailableJobs();
    }

}
