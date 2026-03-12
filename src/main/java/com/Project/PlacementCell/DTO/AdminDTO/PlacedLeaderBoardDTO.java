package com.Project.PlacementCell.DTO.AdminDTO;

import com.Project.PlacementCell.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlacedLeaderBoardDTO {

    private String studentName;
    private Branch branch;
    private String companyName;
    private Double packageLpa;

}