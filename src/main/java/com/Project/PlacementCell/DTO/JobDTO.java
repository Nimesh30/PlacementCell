package com.Project.PlacementCell.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class JobDTO {

    private String companyName;
    private String jobTitle;
    private Double packageLpa;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private Double minCgpa;
    private String description;
    private String eligibleDegrees;
}