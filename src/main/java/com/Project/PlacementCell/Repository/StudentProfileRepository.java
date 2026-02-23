package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.Entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile,Long> {

    Optional<StudentProfile> findByStudentId(String studentId);
}
