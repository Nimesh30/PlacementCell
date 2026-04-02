package com.Project.PlacementCell.DTO.AdminDTO;

import com.Project.PlacementCell.DTO.ApplicationDTO;
import com.Project.PlacementCell.DTO.StudentProfileDTO;
import com.Project.PlacementCell.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AllStudentsDTO {

    // Student
    private String username;
    private String studentId;
    private String email;
    private Boolean placed;

    // Profile (flat)
    @JsonIgnore
    private String fullName;
    @JsonIgnore
    private String mobileNumber;
    @JsonIgnore
    private Double tenthMarks;
    @JsonIgnore
    private Double twelfthMarks;
    @JsonIgnore
    private Double bachelorsCgpa;
    @JsonIgnore
    private String department;
    @JsonIgnore
    private Integer passingYear;

    // Job (flat)
    @JsonIgnore
    private String companyName;
    @JsonIgnore
    private ApplicationStatus status;

    // Nested (for response)
    private StudentProfileDTO profile;
    private List<ApplicationDTO> applications = new ArrayList<>();

    // ✅ THIS IS REQUIRED (MATCH QUERY EXACTLY)
    public AllStudentsDTO(
            String username,
            String studentId,
            String email,
            Boolean placed,
            String fullName,
            String mobileNumber,
            Double tenthMarks,
            Double twelfthMarks,
            Double bachelorsCgpa,
            String department,
            Integer passingYear,
            String companyName,
            ApplicationStatus status
    ) {
        this.username = username;
        this.studentId = studentId;
        this.email = email;
        this.placed = placed;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.tenthMarks = tenthMarks;
        this.twelfthMarks = twelfthMarks;
        this.bachelorsCgpa = bachelorsCgpa;
        this.department = department;
        this.passingYear = passingYear;
        this.companyName = companyName;
        this.status = status;
    }
}