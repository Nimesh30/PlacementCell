package com.Project.PlacementCell.DTO.AdminDTO;

import com.Project.PlacementCell.enums.ApplicationStatus;
import com.Project.PlacementCell.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacedLeaderBoardDTO {

    private String studentName;
    private Branch branch;
    private String companyName;
    private Double packageLpa;
    private LocalDateTime applicationdate;
    private ApplicationStatus status;
}