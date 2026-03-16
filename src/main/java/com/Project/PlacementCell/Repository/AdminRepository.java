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
    s.placed
)
FROM StudentProfile sp
JOIN sp.student s
""")
    List<AllStudentsDTO> getAllStudents();
}
