package com.Project.PlacementCell.Service;


import com.Project.PlacementCell.DTO.ResumeUploadDTO.UploadResponse;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.StudentProfileRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.UploadService.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
//
//@Service
//public class StudentService {
// @Autowired
// private StudentProfileRepository studentProfileRepo;
//    public StudentProfile addStudent(StudentProfile studProfile) {
//
//        System.out.println(studProfile);
//        studentProfileRepo.save(studProfile);
//        return studProfile;
//    }
//}



@Service
public class StudentService {

    @Autowired
    private StudentProfileRepository studentProfileRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UploadService uploadService;

    public StudentProfile addStudent(
            StudentProfile studProfile,
            MultipartFile file,
            Long studentId
    ) throws IOException {

//       Fetch Student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        //Check if profile already exists
//        if (studentProfileRepo.existsByStudent(student)) {
//            throw new RuntimeException("Profile already exists for this student");
//        }

        //  Upload image to Cloudinary
        UploadResponse uploadResponse = uploadService.uploadImage(file);

//          Set image details
        studProfile.setImageUrl(uploadResponse.getImageUrl());
        studProfile.setImagePublicId(uploadResponse.getPublicId());

        //  Set relationship
        studProfile.setStudent(student);

        //  Save to DB
        return studentProfileRepo.save(studProfile);
    }

    public Optional<Student> getStudentbyE(String email) {
        return studentRepository.findByEmail(email);
    }
}