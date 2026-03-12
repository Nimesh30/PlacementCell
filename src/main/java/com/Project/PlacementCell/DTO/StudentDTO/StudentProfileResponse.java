package com.Project.PlacementCell.DTO.StudentDTO;

import com.Project.PlacementCell.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;


// this will fetch student profile from the database...

@Data
@AllArgsConstructor
public class StudentProfileResponse {

    private String studentId;
    private String collegeEmail;

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
    private Branch branch;
    private Integer passingYear;
//    private String imagePublicId;
    private String imageUrl;
}
