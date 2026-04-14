package com.Project.PlacementCell.DTO.AdminDTO;

import lombok.Data;

@Data
public class StudentExportDTO {

    private String studentId;
    private String fullName;
    private String email;
    private String department;
    private String mobileNumber;
    private String stream;
    private String ResumeLink;

    public StudentExportDTO(String studentId, String fullName, String email, String department, String mobileNumber, String stream,String ResumeLink) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.mobileNumber = mobileNumber;
        this.stream = stream;
        this.ResumeLink = ResumeLink;
    }
}
