package com.Project.PlacementCell.DTO.AdminDTO;

import com.Project.PlacementCell.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllStudentsDTO {

    private String studentId;
    private String email;
    private String name;
    private Branch branch;
    private Double cgpa;
    private Integer year;
    private Boolean placed;

}