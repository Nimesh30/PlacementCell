package com.Project.PlacementCell.Repository;

import com.Project.PlacementCell.DTO.CompanyDTO;
import com.Project.PlacementCell.Entity.JobsDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<JobsDetails, Integer> {

    List<JobsDetails> findByDeadlineAfterAndActiveTrueOrderByIdDesc(LocalDate date);
//    Page<JobsDetails> findAllBYOrderByIdDesc(Pageable pageable);
    Page<JobsDetails> findAllByOrderByIdDesc(Pageable pageable);
//
//    List<JobsDetails> findByDeadlineAfterAndActiveTrueAndCompanyNameContainingIgnoreCaseOrRoleContainingIgnoreCaseOrderByIdDesc(
//            LocalDate date, keyword, keyword);

    @Query("SELECT j FROM JobsDetails j " +
            "WHERE j.deadline > :date AND j.active = true " +
            "AND (LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY j.id DESC")

    List<JobsDetails> searchJobs(@Param("date") LocalDate date, @Param("keyword") String keyword);

    @Query("""
            SELECT j FROM JobsDetails j
            WHERE j.active = true
            AND (
            LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            ORDER BY j.id DESC
            """)
    Page<JobsDetails> SearchJobs(@Param("keyword") String keyword, Pageable pageable);

    Optional<JobsDetails> findById(Integer id);

    @Query("SELECT DISTINCT j.companyName FROM JobsDetails j")
    List<String> getDistinctCompanies();

}