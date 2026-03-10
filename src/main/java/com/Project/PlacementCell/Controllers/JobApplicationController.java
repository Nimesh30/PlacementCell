package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.JobAppllicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin("*")
public class JobApplicationController {

    private final JobAppllicationService jobAppllicationService;

    public JobApplicationController(JobAppllicationService jobAppllicationService) {
        this.jobAppllicationService = jobAppllicationService;
    }

    @PostMapping("/apply")
    public String applyJob(@RequestBody ApplyJobDTO dto) {
        return jobAppllicationService.applyForJob(dto);
    }

    @GetMapping("/myApplications/{studentId}")
    public List<AppliedJobDTO> getMyApplications(@PathVariable String studentId) {
        return jobAppllicationService.getMyApplications(studentId);
    }
}
