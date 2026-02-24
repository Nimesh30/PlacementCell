package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.Service.AdminService;
import com.Project.PlacementCell.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return adminService.login(adminLoginDTO);
    }
}
