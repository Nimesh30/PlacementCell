package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AuthDTO.ChangepasswordDTO;
import com.Project.PlacementCell.DTO.AuthDTO.LoginDTO;
import com.Project.PlacementCell.DTO.AuthDTO.RegisterDTO;
import com.Project.PlacementCell.DTO.ResumeUploadDTO.UploadResponse;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.AuthService;
import com.Project.PlacementCell.Service.UploadService.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import com.Project.PlacementCell.Service.Auth.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private UploadService uploadService;

    @PostMapping("/register")

    public ResponseEntity<?> studentRegistration(@RequestBody RegisterDTO registerDTO) {
        return authService.registerStudent(registerDTO);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginStudent(@RequestBody LoginDTO loginDTO) {
        return authService.loginStudentByUname(loginDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangepasswordDTO changePasswordDTO) {
        return authService.changePassword(changePasswordDTO);
    }

//Upload resume on cloudinary...
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("In Image Controller");
//        Map result = uploadService.uploadImage(file);
        UploadResponse response = uploadService.uploadImage(file);
        return ResponseEntity.ok(response);
    }


}
