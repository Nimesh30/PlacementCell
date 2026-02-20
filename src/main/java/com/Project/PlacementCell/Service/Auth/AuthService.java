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
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    public ResponseEntity<?> registerStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already registered!");
        }

        String randomPassword = UUID.randomUUID().toString().substring(0, 8);

        student.setPassword(passwordEncoder.encode(randomPassword));
        student.setFirstLogin(true);

        studentRepository.save(student);

        emailService.sendRegistrationMail(
                student.getEmail(),
                student.getUsername(),
                randomPassword
        );

        return ResponseEntity.ok("User registered successfully. Check your email.");
    }

    // ✅ LOGIN
    public ResponseEntity<?> loginStudentByUname(LoginDTO loginDTO) {

        Optional<Student> optionalStudent =
                studentRepository.findByEmail(loginDTO.getEmail());

        if (optionalStudent.isEmpty()) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials");
        }

        Student student = optionalStudent.get();

        if (!passwordEncoder.matches(
                loginDTO.getPassword(),
                student.getPassword()
        )) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials");
        }

        // ✅ FIRST LOGIN CHECK
        if (Boolean.TRUE.equals(student.getFirstLogin())) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "First login - change password required",
                            "firstLogin", true
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "firstLogin", false
                )
        );
    }
}
