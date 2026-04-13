package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.CompanyDTO;
import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Autowired
    private JobApplicationsRepository jobApplicationsRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentRepository studentRepository;

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

        System.out.println("Job Details: " + job);
        JobsDetails savedJob = jobRepository.save(job);

        // send notification email
        notifyStudents(savedJob);

        return savedJob;
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

    public Page<JobDTO> getAllJobs(String keyword, String status, Pageable pageable) {

        Page<JobsDetails> jobs;

        if (keyword == null) keyword = "";

        jobs = jobRepository.findJobsWithFilter(keyword, status, pageable);

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
        notifyJobUpdate(updated);
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

//It will al students when new job posted...
    public void notifyStudents(JobsDetails job) {

        List<String> studentEmails = studentRepository.getAllStudentEmails();

        for(String email : studentEmails){

            String subject = "New Job Opportunity - " + job.getCompanyName();

            String body = "Dear Student,\n\n"
                    + "A new job has been posted.\n\n"
                    + "Company: " + job.getCompanyName() + "\n"
                    + "Role: " + job.getJobTitle() + "\n"
                    + "Package: " + job.getPackageLpa() + " LPA\n"
                    + "Location: " + job.getLocation() + "\n"
                    + "Apply before: " + job.getDeadline() + "\n\n"
                    + "Login to the portal and apply.\n\n"
                    + "Placement Cell";

            emailService.sendEmail(email, subject, body);
        }
    }


    //It will al students when existing job updated....
    public void notifyJobUpdate(JobsDetails job) {

        List<String> studentEmails = studentRepository.getAllStudentEmails();

        for(String email : studentEmails){

            String subject = "Job Update - " + job.getCompanyName();

            String body = "Dear Student,\n\n"
                    + "The job details have been updated.\n\n"
                    + "Company: " + job.getCompanyName() + "\n"
                    + "Role: " + job.getJobTitle() + "\n"
                    + "Package: " + job.getPackageLpa() + " LPA\n"
                    + "Location: " + job.getLocation() + "\n"
                    + "Deadline: " + job.getDeadline() + "\n\n"
                    + "Please login to the portal to check the updated details.\n\n"
                    + "Placement Cell";

            emailService.sendEmail(email, subject, body);
        }
    }


    public List<Object[]> getCompaniesVisited() {
        return jobRepository. getVisitedCompanies();
    }
}