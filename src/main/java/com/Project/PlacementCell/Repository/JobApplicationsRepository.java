
package com.Project.PlacementCell.Repository;

import ch.qos.logback.core.status.Status;
import com.Project.PlacementCell.DTO.AdminDTO.CompanyWiseHiringDTO;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO;
import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationsRepository extends JpaRepository<JobApplications, Long> {

    boolean existsByStudent_Student_StudentIdAndJob_Id(String studentId, Integer jobId);

    List<ApplyJobDTO> findByStudent_Student_StudentId(String studentId);
    //   List<JobApplications> StudentId(int studentId);

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
    Page<AppliedJobDTO> findJobsAppliedByStudent(String studentId,Pageable pageable);

    @Query("""
            
                SELECT new com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO(
                s.fullName,
                s.branch,
                j.companyName,
                j.packageLpa,
                ja.appliedAt,
                ja.status
            )
            FROM JobApplications ja
            JOIN ja.student s
            JOIN ja.job j
            WHERE ja.status = 'SELECTED' order by j.packageLpa desc
            """)
    List<PlacedLeaderBoardDTO> getPlacedLeaderBoard(Pageable pageable);

    @Query("""
        SELECT COUNT(ja)
        FROM JobApplications ja
        WHERE ja.job.id = :jobId
        """)


    Long countApplicationsByJobId(Integer jobId);


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

    @Query("""
        SELECT new com.Project.PlacementCell.DTO.AdminDTO.CompanyWiseHiringDTO(
        j.companyName,
        COUNT(ja.applicationId),
        j.packageLpa
        )
        FROM JobApplications ja
        JOIN ja.job j
        WHERE ja.status = 'SELECTED'
        GROUP BY j.companyName, j.packageLpa
        """)
    List<CompanyWiseHiringDTO> getCompanyHiringStats();


    @Query("""
            SELECT new com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO(
                s.fullName,
                s.branch,
                j.companyName,
                j.packageLpa,
                ja.appliedAt,
                ja.status
            )
            FROM JobApplications ja
            JOIN ja.student s
            JOIN ja.job j
            WHERE
            (:company IS NULL OR :company = '' OR j.companyName = :company)
            AND
            (:keyword IS NULL OR :keyword = '' 
                OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<PlacedLeaderBoardDTO> getStudentsandCompany(
            String keyword,
            String company,
            Pageable pageable);

    long countByStatus(ApplicationStatus applicationStatus);
}