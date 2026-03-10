package com.Project.PlacementCell.Service.Auth;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.Entity.Admin;
import com.Project.PlacementCell.Repository.AdminRepository;
import com.Project.PlacementCell.config.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private final AdminRepository adminRepository;

    private final AuthUtil authUtil;

    public LoginService(AdminRepository adminRepository, AuthUtil authUtil) {
        this.adminRepository = adminRepository;
        this.authUtil = authUtil;
    }

    public ResponseEntity<?> login(AdminLoginDTO adminLoginDTO){

        Optional<Admin> optionalAdmin =
                adminRepository.findByEmail(adminLoginDTO.getEmail());


        if(optionalAdmin.isEmpty()){
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Invalid credentials"));
        }


        Admin admin = optionalAdmin.get();
        // DO NOT USE regex matches
        if (!adminLoginDTO.getPassword().equals(admin.getPassword())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Invalid credentials"));
        }

        String token = authUtil.generateAccessToken(admin.getAdminid(), "admin");
        return ResponseEntity
                .status(200)
                .body(Map.of(
                        "message", "Login Successful",
                        "username", admin.getName()
                ));
    }
}
