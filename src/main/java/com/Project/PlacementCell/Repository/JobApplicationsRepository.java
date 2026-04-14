package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.DTO.AdminDTO.CompanyWiseHiringDTO;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.AdminDTO.StudentExportDTO;
import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.OfferDTO;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.enums.ApplicationStatus;
import com.Project.PlacementCell.enums.StudentResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationsRepository extends JpaRepository<JobApplications, Long> {

    // ✅ FIXED (removed extra .student)
    boolean existsByStudent_StudentIdAndJob_Id(String studentId, Integer jobId);

    // ❌ REMOVE this (wrong + unused DTO mapping)
    // List<ApplyJobDTO> findByStudent_Student_StudentId(String studentId);

    // ✅ FIXED QUERY
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
        WHERE ja.student.studentId = :studentId
        ORDER BY ja.appliedAt DESC
        """)
    Page<AppliedJobDTO> findJobsAppliedByStudent(String studentId, Pageable pageable);


    // ⚠️ IMPORTANT FIX → StudentProfile needed for fullName & branch
    @Query("""
        SELECT new com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO(
            ja.applicationId,
            sp.fullName,
            sp.branch,
            j.companyName,
            j.packageLpa,
            ja.appliedAt,
            ja.status
        )
        FROM JobApplications ja
        JOIN ja.student s
        JOIN s.profile sp
        JOIN ja.job j
        WHERE ja.status = 'SELECTED'
        ORDER BY j.packageLpa DESC
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
    s.studentId,
    sp.fullName,
    s.email,
    sp.department,
    sp.mobileNumber,
    sp.stream,
    sp.imageUrl
)
FROM JobApplications ja
JOIN ja.student s
JOIN s.profile sp
WHERE ja.job.id = :jobId
ORDER BY ja.appliedAt DESC
""")
    List<StudentExportDTO> getStudentsByJobId(@Param("jobId") Integer jobId);

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
        ORDER BY j.packageLpa DESC
        """)
    List<CompanyWiseHiringDTO> getCompanyHiringStats();


    // ⚠️ FIXED (profile join for name search)
    @Query("""
        SELECT new com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO(
            ja.applicationId,
            sp.fullName, 
            sp.branch,
            j.companyName,
            j.packageLpa,
            ja.appliedAt,
            ja.status
        )
        FROM JobApplications ja
        JOIN ja.student s
        JOIN s.profile sp
        JOIN ja.job j
        WHERE
        (:company IS NULL OR :company = '' OR j.companyName = :company)
        AND
        (:keyword IS NULL OR :keyword = '' OR LOWER(sp.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND 
        (:status IS NULL OR ja.status = :status)
        ORDER BY ja.appliedAt DESC
        """)
    Page<PlacedLeaderBoardDTO> getStudentsandCompany(
            String keyword,
            String company,
            ApplicationStatus status,
            Pageable pageable);


    long countByStatus(ApplicationStatus applicationStatus);


    // ✅ FIXED
    long countByStudent_StudentIdAndStatusAndStudentResponseNot(
            String studentId,
            ApplicationStatus status,
            StudentResponse response
    );


    // ⚠️ FIXED (removed profile navigation)
    @Query("""
        SELECT new com.Project.PlacementCell.DTO.OfferDTO(
            a.applicationId,
            j.companyName,
            j.jobTitle,
            j.packageLpa,
            j.location,
            a.status,
            a.studentResponse,
            a.appliedAt
        )
        FROM JobApplications a
        JOIN a.job j
        JOIN a.student s
        WHERE s.studentId = :studentId  
        AND a.status = 'SELECTED'
        AND a.studentResponse <> 'DECLINED'
        """)
    List<OfferDTO> getSelectedOffers(@Param("studentId") String studentId);


    @Query("""
            SELECT ja FROM JobApplications ja
            WHERE ja.student.id = :studentId
            AND ja.status = 'SELECTED'
            """)
    List<JobApplications> findSelectedByStudentId(@Param("studentId") Long studentId);


}