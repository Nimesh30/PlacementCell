package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/students")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentProfile> addStudent(@ModelAttribute StudentProfile studProfile,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("studentId") Long studentId) throws IOException {

        StudentProfile savedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/getStudentByEmail")
    public ResponseEntity<Optional<Student>> getStudentsByEmail(
            @RequestParam String email) {

        Optional<Student> student = studentService.getStudentbyE(email);
        return ResponseEntity.ok(student);
    }
}
