package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@StudentController
@RequestMapping("/api/jobs")
@CrossOrigin("*")
@RestController
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
    public List<JobsDetails> getAvailableJobs(@RequestParam(required = false) String keyword) {
        // Service internally uses LocalDate.now()
        return jobService.getAvailableJobs(keyword);
    }

//    @PostMapping("/applyjob"){
//
//    }

}
