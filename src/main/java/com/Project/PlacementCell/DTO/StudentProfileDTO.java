package com.Project.PlacementCell.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfileDTO {

    private String fullName;
    private String mobileNumber;
    private Double tenthMarks;
    private Double twelfthMarks;
    private Double bachelorsCgpa;
    private String department;
    private Integer passingYear;



}
