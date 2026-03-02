package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.Entity.Admin;
import com.Project.PlacementCell.Repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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

        return ResponseEntity
                .status(200)
                .body(Map.of(
                        "message", "Login Successful",
                        "username", admin.getName()
                ));
    }
}
