package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Admin Add Job
    public JobsDetails addJob(JobDTO dto) {

        JobsDetails job = new JobsDetails();
        job.setCompanyName(dto.getCompanyName());
        job.setJobTitle(dto.getJobTitle());
        job.setPackageLpa(dto.getPackageLpa());
        job.setLocation(dto.getLocation());
        job.setDeadline(dto.getDeadline());
        job.setMinCgpa(dto.getMinCgpa());
        job.setDescription(dto.getDescription());
        job.setTrainingDetails(dto.getTrainingDetails());
        job.setGrowthPath(dto.getGrowthPath());
        job.setEligibleDegrees(dto.getEligibleDegrees());

        return jobRepository.save(job);
    }

    // Get Only Active & Not Expired Jobs
    public List<JobsDetails> getAvailableJobs() {
        return jobRepository.findByDeadlineAfterAndActiveTrue(LocalDate.now());
    }
}