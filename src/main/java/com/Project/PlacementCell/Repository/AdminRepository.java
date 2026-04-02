package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO;
import com.Project.PlacementCell.Entity.Admin;
import com.Project.PlacementCell.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String username);

//    @Query("""
//    SELECT new com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO(
//        s.studentId,
//        s.email,
//        COALESCE(sp.fullName, null),
//        COALESCE(sp.branch, null),
//        COALESCE(sp.bachelorsCgpa, null),
//        COALESCE(sp.passingYear, null),
//        CASE
//            WHEN COUNT(CASE WHEN ja.status = 'SELECTED' THEN 1 END) > 0
//            THEN com.Project.PlacementCell.enums.ApplicationStatus.SELECTED
//            ELSE com.Project.PlacementCell.enums.ApplicationStatus.APPLIED
//        END
//    )
//    FROM Student s
//    LEFT JOIN s.profile sp
//    LEFT JOIN JobApplications ja ON ja.student = s
//    GROUP BY
//        s.studentId,
//        s.email,
//        sp.fullName,
//        sp.branch,
//        sp.bachelorsCgpa,
//        sp.passingYear
//""")
//    List<AllStudentsDTO> getAllStudents();


    @Query("""
SELECT new com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO(
    s.username,
    s.studentId,
    s.email,
    s.placed,
    sp.fullName,
    sp.mobileNumber,
    sp.tenthMarks,
    sp.twelfthMarks,
    sp.bachelorsCgpa,
    sp.department,
    sp.passingYear,
    ja.job.companyName,
    ja.status
)
FROM Student s
LEFT JOIN s.profile sp
LEFT JOIN s.applications ja
""")
    List<AllStudentsDTO> getAllStudents();

}