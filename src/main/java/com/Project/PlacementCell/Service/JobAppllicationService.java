package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentProfileRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobAppllicationService {

    private final JobApplicationsRepository jobApplicationsRepository;
    private final StudentProfileRepository studentRepository;
    private final JobRepository jobRepository;

    public JobAppllicationService(JobApplicationsRepository jobApplicationsRepository,
                                  StudentProfileRepository studentRepository,
                                  JobRepository jobRepository) {
        this.jobApplicationsRepository = jobApplicationsRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
    }

    public ResponseEntity<?> applyForJob(ApplyJobDTO dto) {

        String studentId = dto.getStudentId();
        Integer jobId = dto.getJobId();

        if (jobApplicationsRepository.existsByStudent_Student_StudentIdAndJob_Id(studentId, jobId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "You already applied for this job"));
        }

        StudentProfile student = studentRepository
                .findByStudent_StudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        JobsDetails job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplications application = new JobApplications();
        application.setStudent(student);
        application.setJob(job);

        jobApplicationsRepository.save(application);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Successfully Applied"));
    }

    public Page<AppliedJobDTO> getMyApplications(String studentId, Pageable pageable) {
        return jobApplicationsRepository.findJobsAppliedByStudent(studentId, pageable);
    }

}