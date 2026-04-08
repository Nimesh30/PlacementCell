package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.Entity.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //This
    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentId(String studentId);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.profile")
    List<Student> findAllWithProfile();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.profile WHERE s.placed = :placed")
    List<Student> findByPlacedWithProfile(boolean placed);

    long countByPlacedTrue();

    @Query("SELECT s.email FROM Student s")
    List<String> getAllStudentEmails();

    boolean existsByStudentId(@NotBlank(message = "Student ID is required") String studentId);
}
