package com.Project.PlacementCell.Service;


import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.EmailService;
import com.Project.PlacementCell.Service.Auth.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public StudentService(StudentRepository repository,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    public Student register(Student student) {

        if (repository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String rawPassword = PasswordGenerator.generatePassword(8);

        student.setPassword(passwordEncoder.encode(rawPassword));
        student.setFirstLogin(true);

        Student saved = repository.save(student);

        emailService.sendRegistrationMail(
                saved.getEmail(),
                saved.getUsername(),
                rawPassword
        );

        return saved;
    }
}