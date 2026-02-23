package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.Entity.JobsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobRepository extends JpaRepository<JobRepository, Integer>{
    List<JobsDetails> findByDeadlineAfterAndActiveTrue(LocalDate date);
}
