package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.LoginDTO;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.AuthService;
import com.Project.PlacementCell.Service.Auth.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")

    public void studentRegistration(@RequestBody Student student) {
        authService.registerStudent(student);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO loginDTO) {
        return authService.loginStudentByUname(loginDTO);
    }
}
