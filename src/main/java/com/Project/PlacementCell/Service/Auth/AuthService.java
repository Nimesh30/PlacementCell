package com.Project.PlacementCell.Service.Auth;

import com.Project.PlacementCell.DTO.AuthDTO.ChangepasswordDTO;
import com.Project.PlacementCell.DTO.AuthDTO.LoginDTO;
import com.Project.PlacementCell.DTO.AuthDTO.RegisterDTO;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    // REGISTER
    public ResponseEntity<?> registerStudent(RegisterDTO registerDTO) {

        if (studentRepository.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email already registered!"));
        }

        String randomPassword = UUID.randomUUID().toString().substring(0, 8);

        Student student = new Student();
        student.setUsername(registerDTO.getUsername());
        student.setStudentId(registerDTO.getStudentId());
        student.setEmail(registerDTO.getEmail());
        student.setPassword(passwordEncoder.encode(randomPassword));
        student.setFirstLogin(true);
        System.out.println("In register Student "+ student);
        studentRepository.save(student);

        emailService.sendRegistrationMail(
                student.getEmail(),
                student.getUsername(),
                randomPassword
        );

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully. Check your email.")
        );
    }

    //LoginUser
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

        // FIRST LOGIN CHECK
        if (Boolean.TRUE.equals(student.getFirstLogin())) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "First login - change password required",
                            "firstLogin", true,
                            "studentId", student.getStudentId()
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

    public ResponseEntity<?> changePassword(ChangepasswordDTO changePasswordDTO) {

        Optional<Student> optionalStudent =
                studentRepository.findByEmail(changePasswordDTO.getEmail());

        if (optionalStudent.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "User not found"));
        }

        Student student = optionalStudent.get();

        student.setPassword(
                passwordEncoder.encode(changePasswordDTO.getNewPassword())
        );

        student.setFirstLogin(false);

        studentRepository.save(student);

        return ResponseEntity.ok(
                Map.of("message", "Password changed successfully")
        );
    }
}
