package com.Project.PlacementCell.DTO.AdminDTO;

import lombok.Data;

@Data
public class StudentExportDTO {

    private String studentId;
    private String fullname;
    private String email;
    private String department;
    private String mobileNumber;
    private String stream;

    public StudentExportDTO(String studentId, String fullname, String email, String department, String mobileNumber, String stream) {
        this.studentId = studentId;
        this.fullname = fullname;
        this.email = email;
        this.department = department;
        this.mobileNumber = mobileNumber;
        this.stream = stream;
    }
}
