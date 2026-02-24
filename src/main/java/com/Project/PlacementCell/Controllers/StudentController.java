package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentProfile> addStudent(@ModelAttribute StudentProfile studProfile,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("studentId") Long studentId) throws IOException {

        System.out.println("In Add student Controller");
        StudentProfile savedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(savedProfile);
    }
}
