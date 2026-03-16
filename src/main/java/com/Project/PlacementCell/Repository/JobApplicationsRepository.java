package com.Project.PlacementCell.Repository;

import ch.qos.logback.core.status.Status;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.AppliedJobDTO;
import com.Project.PlacementCell.DTO.JobDTO;
import com.Project.PlacementCell.DTO.StudentDTO.ApplyJobDTO;
import com.Project.PlacementCell.Entity.JobApplications;
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
    List<AppliedJobDTO> findJobsAppliedByStudent(String studentId);

    @Query("""

            SELECT new com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO(
    s.fullName,
    s.branch,
    j.companyName,
    j.packageLpa
)
FROM JobApplications ja
JOIN ja.student s
JOIN ja.job j
WHERE ja.status = 'SELECTED'
""")
    List<PlacedLeaderBoardDTO> getPlacedLeaderBoard();
}
