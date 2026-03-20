package com.Project.PlacementCell.Service.Auth;

import com.Project.PlacementCell.DTO.AuthDTO.ChangepasswordDTO;
import com.Project.PlacementCell.DTO.AuthDTO.LoginDTO;
import com.Project.PlacementCell.DTO.AuthDTO.RegisterDTO;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.config.AuthUtil;
//import com.Project.PlacementCell.error.EmailAlreadyExistsException;
//import com.Project.PlacementCell.error.InvalidCredentialsException;
//import com.Project.PlacementCell.error.UserNotFoundException;
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
    @Autowired
    private AuthUtil authUtil;


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
    public ResponseEntity<?> loginStudentByEmail(LoginDTO loginDTO) {

        Optional<Student> optionalStudent =
                studentRepository.findByEmail(loginDTO.getEmail());

        if (optionalStudent.isEmpty()) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials.......is empty");
        }

        Student student = optionalStudent.get();

        if (!passwordEncoder.matches(
                loginDTO.getPassword(),
                student.getPassword()
        )) {
            return ResponseEntity
                    .status(401)
                    .body("You entered diffferent password..");
        }

        // FIRST LOGIN CHECK
        if (Boolean.TRUE.equals(student.getFirstLogin())) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "First login - change password required",
                            "firstLogin", true
                    )
            );
        }

        String token =authUtil.generateAccessToken(student.getId(), "STUDENT");
        return ResponseEntity.ok(
                Map.of(
                        "studentId", student.getStudentId(),
                        "username", student.getUsername(),
                        "email", student.getEmail(),
                        "token",token,
                        "Role","STUDENT",
                        "firstLogin", false,
                        "message", "Login successful"
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