package com.Project.PlacementCell.DTO.StudentDTO;

import lombok.Data;

@Data
public class StudentProfileDTO {
    private String studentId;
    private String fullName;
    private String personalEmail;
    private String mobileNumber;

    private Double tenthMarks;
    private Double twelfthMarks;
    private String stream;

    private Double bachelorsCgpa;
    private Double mastersCgpa;

    private String institute;
    private String department;
    private Integer passingYear;

    private Long studentDbId;

}
