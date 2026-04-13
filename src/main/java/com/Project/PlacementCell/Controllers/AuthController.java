package com.Project.PlacementCell.Controllers;
import com.Project.PlacementCell.DTO.AuthDTO.ChangepasswordDTO;
import com.Project.PlacementCell.DTO.AuthDTO.LoginDTO;
import com.Project.PlacementCell.DTO.AuthDTO.RegisterDTO;
import com.Project.PlacementCell.DTO.AuthDTO.ResetPasswordDTO;
import com.Project.PlacementCell.DTO.ResumeUploadDTO.UploadResponse;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.Service.Auth.AuthService;
import com.Project.PlacementCell.Service.UploadService.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

//@StudentController
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
        return authService.loginStudentByEmail(loginDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangepasswordDTO changePasswordDTO) {
        System.out.println("in controller..."+changePasswordDTO.getNewPassword());
        return authService.changePassword(changePasswordDTO);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam String email ) {
        return authService.forgotPassword(email);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {

        return authService.resetPassword(dto.getToken(), dto.getNewPassword());

    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("In Image Controller");
//        Map result = uploadService.uploadImage(file);
        UploadResponse response = uploadService.uploadImage(file);
        return ResponseEntity.ok(response);
    }
}
