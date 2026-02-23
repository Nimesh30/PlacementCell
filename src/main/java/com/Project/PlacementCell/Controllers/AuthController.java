package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AuthDTO.ChangepasswordDTO;
import com.Project.PlacementCell.DTO.AuthDTO.LoginDTO;
import com.Project.PlacementCell.DTO.AuthDTO.RegisterDTO;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")

    public ResponseEntity<?> studentRegistration(@RequestBody RegisterDTO registerDTO) {
        return authService.registerStudent(registerDTO);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO loginDTO) {
        return authService.loginStudentByUname(loginDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangepasswordDTO changePasswordDTO) {
        return authService.changePassword(changePasswordDTO);
    }
}
