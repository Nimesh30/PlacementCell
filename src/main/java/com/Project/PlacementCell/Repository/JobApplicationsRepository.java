package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO;
import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationsRepository extends JpaRepository<JobApplications, Long> {

    boolean existsByStudent_Student_StudentIdAndJob_Id(String studentId, Integer jobId);

    //Fetch Job Apply by student


    @Query("""
        SELECT new com.Project.PlacementCell.DTO.AppliedJobDTO(
            j.id,
            j.companyName,
            j.jobTitle,
            j.packageLpa,
            j.location,
            ja.appliedAt,
            ja.status
        )
        FROM JobApplications ja
        JOIN ja.job j
        WHERE ja.student.student.studentId = :studentId
        ORDER BY ja.appliedAt DESC
        """)
    Page<AppliedJobDTO> findJobsAppliedByStudent(@Param("studentId") String studentId, Pageable pageable);


//    @Query("""
//    SELECT new com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO(
//        s.student.studentId,
//        s.name,
//        s.email,
//        s.branch,
//        s.phone,
//        ja.status
//    )
//    FROM JobApplications ja
//    JOIN ja.student s
//    WHERE ja.job.id = :jobId """)
//    List<StudentExportDTO> getStudentsByJobId(Integer jobId);


    @Query("""
            SELECT new com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO(
                ja.student.student.studentId,
                ja.student.fullName,
                ja.student.student.email,
                ja.student.department,
                ja.student.mobileNumber,
                ja.student.stream
            )
            FROM JobApplications ja
            WHERE ja.job.id = :jobId
            ORDER BY ja.appliedAt DESC
            """)
    List<StudentExportDTO> getStudentsByJobId(Integer jobId);


//    count totalapplication job wise
    @Query("""
            SELECT COUNT(ja)
            FROM JobApplications ja
            WHERE ja.job.id = :jobId
            """)
    Long countApplicationsByJobId(Integer jobId);

   //
    //Long countJobApplicationsByJobId(Integer jobId);


}
