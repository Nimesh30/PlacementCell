package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.OfferDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.JobsDetails;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentProfileRepository;

import com.Project.PlacementCell.enums.ApplicationStatus;
import com.Project.PlacementCell.enums.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
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

        if (jobApplicationsRepository.existsByStudent_StudentIdAndJob_Id(studentId, jobId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "You already applied for this job"));
        }

        // 🔥 get profile first
        StudentProfile profile = studentRepository
                .findByStudent_StudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 🔥 extract student
        Student student = profile.getStudent();

        JobsDetails job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplications application = new JobApplications();
        application.setStudent(student); // ✅ FIXED
        application.setJob(job);

        jobApplicationsRepository.save(application);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Successfully Applied"));
    }

    public Page<AppliedJobDTO> getMyApplications(String studentId, Pageable pageable) {
        return jobApplicationsRepository.findJobsAppliedByStudent(studentId,pageable);
    }

    public Page<PlacedLeaderBoardDTO> getStudentsandCompany(
            String keyword,
            String company,
            ApplicationStatus status,
            Pageable page) {

        return jobApplicationsRepository.getStudentsandCompany(keyword, company,status, page);
    }

    public void updateStatus(List<Long> ids, String status) {

        ApplicationStatus newStatus = ApplicationStatus.valueOf(status);

        List<JobApplications> applications = jobApplicationsRepository.findAllById(ids);

        for (JobApplications app : applications) {
            app.setStatus(newStatus);

            if(newStatus == ApplicationStatus.SELECTED && app.getStudentResponse() == null){
                app.setStudentResponse(StudentResponse.PENDING);
            }
            else {
                app.setStudentResponse(null);
            }
        }

        jobApplicationsRepository.saveAll(applications);
    }

    public long getSelectedCount(String studentId) {
        return jobApplicationsRepository.countByStudent_StudentIdAndStatusAndStudentResponseNot(
                studentId,
                ApplicationStatus.SELECTED,
                StudentResponse.DECLINED
        );
    }

    public List<OfferDTO> getSelectedOffers(String studentId) {
        return jobApplicationsRepository.getSelectedOffers(studentId);
    }

    // ✅ Accept / Reject
    public void updateStudentResponse(Long id, String response) {
        JobApplications app = jobApplicationsRepository.findById(id).orElseThrow();

        app.setStudentResponse(StudentResponse.valueOf(response));

        jobApplicationsRepository.save(app);
    }

}