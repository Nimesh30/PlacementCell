package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public List<JobDTO> getAvailableJobs(@RequestParam(required = false) String keyword) {
        // Service internally uses LocalDate.now()
        return jobService.getAvailableJobs(keyword);
    }


    @GetMapping("/alljobs")
    public Page<JobDTO> getAllJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "all") String status,
            Pageable pageable) {

        System.out.println("STATUS: " + status);
        return jobService.getAllJobs(keyword, status, pageable);
    }


    @DeleteMapping("deletejob/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job and related applications deleted successfully");
    }

    @PutMapping("updatejob/{id}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable Integer id,
            @RequestBody JobDTO dto
    ) {
        return ResponseEntity.ok(jobService.updateJob(id, dto));
    }


}
