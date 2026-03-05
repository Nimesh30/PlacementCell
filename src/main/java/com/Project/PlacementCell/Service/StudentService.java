package com.Project.PlacementCell.Service;


import com.Project.PlacementCell.DTO.ResumeUploadDTO.UploadResponse;
//import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Repository.StudentProfileRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.UploadService.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentProfileRepository studentProfileRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UploadService uploadService;
    public StudentProfileResponse getProfileByStudentId(String studentId) {

        Optional<StudentProfile> optionalProfile =
                studentProfileRepo.findByStudent_StudentId(studentId);

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // If profile exists
        if (optionalProfile.isPresent()) {
            System.out.println("Profile Exist...");
            StudentProfile profile = optionalProfile.get();

            return new StudentProfileResponse(
                    student.getStudentId(),
                    student.getEmail(),

                    profile.getFullName(),
                    profile.getPersonalEmail(),
                    profile.getMobileNumber(),

                    profile.getTenthMarks(),
                    profile.getTwelfthMarks(),
                    profile.getStream(),

                    profile.getBachelorsCgpa(),
                    profile.getMastersCgpa(),

                    profile.getInstitute(),
                    profile.getDepartment(),
                    profile.getPassingYear(),
                    profile.getImageUrl()
            );
        }

        // If profile does NOT exist (first time user)
        return new StudentProfileResponse(
                student.getStudentId(),
                student.getEmail(),

                null, null, null,
                null, null, null,
                null, null,
                null, null, null,
                null
        );
    }

    public StudentProfile addStudent(
            StudentProfile studProfile,
            MultipartFile file,
            String studentId
    ) throws IOException {

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Optional<StudentProfile> existingProfile =
                studentProfileRepo.findByStudent_StudentId(studentId);

        StudentProfile profile;

        if (existingProfile.isPresent()) {
            System.out.println("Update profile , UPDATE");
            profile = existingProfile.get(); // UPDATE
        } else {
            System.out.println("Create Profile , else part");
            profile = new StudentProfile(); // CREATE
            profile.setStudent(student);
        }

        // Set all fields
        profile.setFullName(studProfile.getFullName());
        profile.setPersonalEmail(studProfile.getPersonalEmail());
        profile.setMobileNumber(studProfile.getMobileNumber());
        profile.setTenthMarks(studProfile.getTenthMarks());
        profile.setTwelfthMarks(studProfile.getTwelfthMarks());
        profile.setStream(studProfile.getStream());
        profile.setBachelorsCgpa(studProfile.getBachelorsCgpa());
        profile.setMastersCgpa(studProfile.getMastersCgpa());
        profile.setInstitute(studProfile.getInstitute());
        profile.setDepartment(studProfile.getDepartment());
        profile.setPassingYear(studProfile.getPassingYear());

        if (file != null && !file.isEmpty()) {
            UploadResponse uploadResponse = uploadService. uploadFile(file);
            profile.setImageUrl(uploadResponse.getImageUrl());
            profile.setImagePublicId(uploadResponse.getPublicId());
        }

        return studentProfileRepo.save(profile);
    }

    public Optional<Student> getStudentbyE(String email) {
        return studentRepository.findByEmail(email);
    }
}