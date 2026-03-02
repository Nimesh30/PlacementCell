package com.Project.PlacementCell.Controllers;

//import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.StudentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentProfile> addStudent(@ModelAttribute StudentProfile studProfile,
                                                     @RequestParam("file") MultipartFile file,
                                                     @RequestParam("studentId") String studentId) throws IOException {

        System.out.println("In Add student Controller");
        StudentProfile savedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/profile/{studentId}")
    public ResponseEntity<StudentProfileResponse> getProfile(
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(
                studentService.getProfileByStudentId(studentId)
        );
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<StudentProfile> updateStudent(
            @ModelAttribute StudentProfile studProfile,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable String studentId) throws IOException {

        StudentProfile updatedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(updatedProfile);
    }

}

