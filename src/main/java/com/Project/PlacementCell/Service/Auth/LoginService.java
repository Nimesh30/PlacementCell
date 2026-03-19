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

        // ⚠️ NOTE: You should use passwordEncoder here ideally
        if (!adminLoginDTO.getPassword().equals(admin.getPassword())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Invalid credentials"));
        }

        // ✅ FIX: use uppercase role
        String token = authUtil.generateAccessToken(admin.getAdminid(), "ADMIN");

        // ✅ FIX: return token + role + email etc.
        return ResponseEntity.ok(
                Map.of(
                        "adminId", admin.getAdminid(),
                        "username", admin.getName(),
                        "email", admin.getEmail(),
                        "token", token,
                        "Role", "ADMIN",
                        "message", "Login Successful"
                )
        );
    }
}
