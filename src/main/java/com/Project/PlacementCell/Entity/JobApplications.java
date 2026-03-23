package com.Project.PlacementCell.Entity;

import com.Project.PlacementCell.enums.ApplicationStatus;
import com.Project.PlacementCell.enums.StudentResponse;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "job_applications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id","job_id"})
        }
)
public class JobApplications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private JobsDetails job;

//    @Column(nullable=false)
//    private String status = "APPLIED";   // default value

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Enumerated(EnumType.STRING)
    private StudentResponse studentResponse;
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime appliedAt;

    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;
}
