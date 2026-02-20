package com.Project.PlacementCell.Service.Auth;

import com.Project.PlacementCell.DTO.LoginDTO;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerStudent(@RequestBody Student student) {

        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered!");
        }

        // Generate random password
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);

        // Encode password
        student.setPassword(passwordEncoder.encode(randomPassword));
        student.setFirstLogin(true);

        // Save user
        studentRepository.save(student);

        // Send email
        emailService.sendRegistrationMail(student.getEmail(), student.getUsername(), randomPassword);

        return ResponseEntity.ok("User registered successfully. Check your email.");
    }

    public ResponseEntity<?> loginStudentByUname(LoginDTO loginDTO) {

        Student student = studentRepository.findByEmail(loginDTO.getEmail());

        if (student == null) {
            return ResponseEntity.badRequest().body("Invalid Email");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), student.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid Password");
        }

        if (Boolean.TRUE.equals(student.getFirstLogin())) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "First Login - Change Password Required",
                            "firstLogin", true
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login Successful",
                        "firstLogin", false
                )
        );
    }


}
