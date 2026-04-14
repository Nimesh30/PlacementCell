package com.Project.PlacementCell.DTO.AuthDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Student ID is required")
    private String studentId;
    //    private  String password;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@charusat\\.edu\\.in$",
            message = "Email must be a valid Charusat email (example: 24msit130@charusat.edu.in)"
    )
    private String email;
}
