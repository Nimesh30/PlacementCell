package com.Project.PlacementCell.DTO.AuthDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {

//    private int studentid;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@charusat\\.edu\\.in$",
            message = "Email must be a valid Charusat email (example: 24msit130@charusat.edu.in)"
    )
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
//    private String studentId;
}
