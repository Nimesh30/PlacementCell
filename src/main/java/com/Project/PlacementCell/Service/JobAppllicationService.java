package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentProfileRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

//    public String applyForJob(ApplyJobDTO dto) {
//
//        String studentId = dto.getStudentId();
//        Integer jobId = dto.getJobId();
//
//        if(jobApplicationsRepository.existsByStudent_StudentIdAndJob_Id(studentId,jobId)){
//            return "You already applied for this job";
//        }
//
//        StudentProfile student = studentRepository.findByStudent_StudentId(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        JobsDetails job = jobRepository.findById(jobId)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        JobApplications application = new JobApplications();
//        application.setStudent(student);
//        application.setJob(job);
//      //  application.setStatus("APPLIED");
//
//        jobApplicationsRepository.save(application);
////        jobApplicationsRepository.save(application);
//
//        return "Job applied successfully";
//    }

public String applyForJob(ApplyJobDTO dto) {

        String studentId = dto.getStudentId();
        Integer jobId = dto.getJobId();

        if(jobApplicationsRepository.existsByStudent_Student_StudentIdAndJob_Id(studentId, jobId)){
            return "You already applied for this job";
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

        return "Job applied successfully";
    }

    public List<AppliedJobDTO> getMyApplications(String studentId) {

        return jobApplicationsRepository
                .findJobsAppliedByStudent(studentId);
    }
}
