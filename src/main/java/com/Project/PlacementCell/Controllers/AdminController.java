
package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.Service.AdminServices;
import com.Project.PlacementCell.Service.Auth.LoginService;
import com.Project.PlacementCell.Service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private ExportService exportService;

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return loginService.login(adminLoginDTO);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return adminServices.getDashboard();
    }


    @GetMapping("/students")
    public ResponseEntity<?> getStudents() {
        return adminServices.getStudents();
    }


    @GetMapping("/export/{jobId}")
    public ResponseEntity<InputStreamResource> exportStudents(@PathVariable Integer jobId) throws IOException {

        ByteArrayInputStream stream = exportService.exportStudents(jobId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }
}