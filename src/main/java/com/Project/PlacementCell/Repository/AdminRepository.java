package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO;
import com.Project.PlacementCell.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String username);



    @Query("""
SELECT new com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO(
    s.studentId,
    s.email,
    sp.fullName,
    sp.branch,
    sp.bachelorsCgpa,
    sp.passingYear,
    CASE 
        WHEN COUNT(CASE WHEN ja.status = 'SELECTED' THEN 1 END) > 0 
        THEN com.Project.PlacementCell.enums.ApplicationStatus.SELECTED
        ELSE com.Project.PlacementCell.enums.ApplicationStatus.APPLIED
    END
)
FROM StudentProfile sp
JOIN sp.student s
LEFT JOIN JobApplications ja ON ja.student = sp
GROUP BY 
    s.studentId,
    s.email,
    sp.fullName,
    sp.branch,
    sp.bachelorsCgpa,
    sp.passingYear
""")
    List<AllStudentsDTO> getAllStudents();
}