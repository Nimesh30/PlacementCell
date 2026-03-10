package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.Entity.JobsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<JobsDetails, Integer> {

    List<JobsDetails> findByDeadlineAfterAndActiveTrueOrderByIdDesc(LocalDate date);
//
//    List<JobsDetails> findByDeadlineAfterAndActiveTrueAndCompanyNameContainingIgnoreCaseOrRoleContainingIgnoreCaseOrderByIdDesc(
//            LocalDate date, keyword, keyword);

    @Query("SELECT j FROM JobsDetails j " +
            "WHERE j.deadline > :date AND j.active = true " +
            "AND (LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY j.id DESC")

    List<JobsDetails> searchJobs(@Param("date") LocalDate date, @Param("keyword") String keyword);

    Optional<JobsDetails> findById(Integer id);

}