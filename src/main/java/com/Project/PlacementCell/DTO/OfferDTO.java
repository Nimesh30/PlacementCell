package com.Project.PlacementCell.DTO;

import com.Project.PlacementCell.enums.ApplicationStatus;
import com.Project.PlacementCell.enums.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OfferDTO {
    private Long applicationId;
    private String companyName;
    private String jobTitle;
    private Double packageLpa;
    private String location;
    private ApplicationStatus status;
    private StudentResponse studentResponse;
    private LocalDateTime appliedAt;
}
