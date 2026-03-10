package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.Service.AdminServices;
import com.Project.PlacementCell.Service.Auth.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminServices adminServices;

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return loginService.login(adminLoginDTO);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return adminServices.getDashboard();
    }
}
