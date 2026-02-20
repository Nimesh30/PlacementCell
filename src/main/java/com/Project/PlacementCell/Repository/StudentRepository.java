package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //This
    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);
}
