package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.CompanyDTO;
import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Autowired
    private JobApplicationsRepository jobApplicationsRepository;

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
        job.setEligibleDegrees(dto.getEligibleDegrees());
        job.setActive(true);

        return jobRepository.save(job);
    }

    // Get only active & not expired jobs and optionally search by keyword
    // Get jobs with application count
    public List<JobDTO> getAvailableJobs(String keyword) {

        List<JobsDetails> jobs;

        if (keyword == null || keyword.isEmpty()) {
            jobs = jobRepository.findByDeadlineAfterAndActiveTrueOrderByIdDesc(LocalDate.now());
        } else {
            jobs = jobRepository.searchJobs(LocalDate.now(), keyword);
        }

        List<JobDTO> jobDTOList = new ArrayList<>();

        for (JobsDetails job : jobs) {

            JobDTO dto = new JobDTO();

            dto.setId(job.getId());
            dto.setCompanyName(job.getCompanyName());
            dto.setJobTitle(job.getJobTitle());
            dto.setPackageLpa(job.getPackageLpa());
            dto.setLocation(job.getLocation());
            dto.setDeadline(job.getDeadline());
            dto.setMinCgpa(job.getMinCgpa());
            dto.setDescription(job.getDescription());
            dto.setEligibleDegrees(job.getEligibleDegrees());

            // 👇 count applications
            dto.setApplicationCount(
                    jobApplicationsRepository.countApplicationsByJobId(Math.toIntExact(job.getId()))
            );

            jobDTOList.add(dto);
        }

        return jobDTOList;
    }

    public Page<JobDTO> getAllJobs(String keyword, Pageable pageable) {

        Page<JobsDetails> jobs;

        if (keyword == null || keyword.trim().isEmpty()) {
            jobs = jobRepository.findAllByOrderByIdDesc(pageable);
        } else {
            jobs = jobRepository.SearchJobs(keyword, pageable);
        }

        return jobs.map(job -> {

            JobDTO dto = new JobDTO();

            dto.setId(job.getId());
            dto.setCompanyName(job.getCompanyName());
            dto.setJobTitle(job.getJobTitle());
            dto.setPackageLpa(job.getPackageLpa());
            dto.setLocation(job.getLocation());
            dto.setDeadline(job.getDeadline());
            dto.setMinCgpa(job.getMinCgpa());
            dto.setDescription(job.getDescription());
            dto.setEligibleDegrees(job.getEligibleDegrees());

            // Count applications for this job
            dto.setApplicationCount(
                    jobApplicationsRepository.countApplicationsByJobId(job.getId().intValue())
            );

            return dto;
        });
    }

    public List<String> getAllComapnies(){
//        List<CompanyDTO> findAllCompanies();
        return jobRepository. getDistinctCompanies();
    }

    public void deleteJob(Integer jobId) {

        JobsDetails job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Cascade will delete applications automatically
        jobRepository.delete(job);
    }

    public JobDTO updateJob(Integer id, JobDTO dto) {

        JobsDetails job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setCompanyName(dto.getCompanyName());
        job.setJobTitle(dto.getJobTitle());
        job.setPackageLpa(dto.getPackageLpa());
        job.setLocation(dto.getLocation());
        job.setMinCgpa(dto.getMinCgpa());
        job.setEligibleDegrees(dto.getEligibleDegrees());
        job.setDeadline(dto.getDeadline());
        job.setDescription(dto.getDescription());

        JobsDetails updated = jobRepository.save(job);

        // convert to DTO
        JobDTO response = new JobDTO();
        response.setId(updated.getId());
        response.setCompanyName(updated.getCompanyName());
        response.setJobTitle(updated.getJobTitle());
        response.setPackageLpa(updated.getPackageLpa());
        response.setLocation(updated.getLocation());
        response.setMinCgpa(updated.getMinCgpa());
        response.setEligibleDegrees(updated.getEligibleDegrees());
        response.setDeadline(updated.getDeadline());
        response.setDescription(updated.getDescription());

        return response;
    }
}