package com.Project.PlacementCell.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class JobDTO {

    private String companyName;
    private String jobTitle;
    private Double packageLpa;
    private String location;
    private LocalDate deadline;
    private Double minCgpa;
    private String description;
    private String trainingDetails;
    private String growthPath;
    private String eligibleDegrees;
}