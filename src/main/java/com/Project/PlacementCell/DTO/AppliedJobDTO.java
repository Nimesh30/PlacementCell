package com.Project.PlacementCell.DTO;

import com.Project.PlacementCell.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJobDTO {
    private Long id;
    private String companyName;
    private String jobTitle;
    private Double packageLpa;
    private String location;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
}
