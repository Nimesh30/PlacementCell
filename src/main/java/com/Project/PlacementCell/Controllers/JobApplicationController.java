package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.StatusUpdateDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.JobAppllicationService;
//import org.springframework.data.domain.Pageable;
import com.Project.PlacementCell.enums.ApplicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

//import java.awt.print.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> applyJob(@RequestBody ApplyJobDTO dto) {
        return jobAppllicationService.applyForJob(dto);
    }

    @GetMapping("/myApplications/{studentId}")
    public Page<AppliedJobDTO> getMyApplications(
            @PathVariable String studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return jobAppllicationService.getMyApplications(studentId,pageable);
    }


        @GetMapping("/status")
        public ApplicationStatus[] getAllStatuses() {
            return ApplicationStatus.values();
        }

        @PutMapping("/updatestatus")
        public String updateStatus(@RequestBody StatusUpdateDTO dto) {
            jobAppllicationService.updateStatus(dto.getIds(), dto.getStatus());
            return "Status Updated Successfully";
        }

}
