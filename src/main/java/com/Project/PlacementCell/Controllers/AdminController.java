
package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AdminDTO.AdminLoginDTO;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.Service.AdminServices;
import com.Project.PlacementCell.Service.Auth.LoginService;
import com.Project.PlacementCell.Service.ExportService;
import com.Project.PlacementCell.Service.JobAppllicationService;
import com.Project.PlacementCell.Service.JobService;
import com.Project.PlacementCell.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

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
    @Autowired
    private JobService jobService;

    @Autowired
    private JobAppllicationService  jobAppllicationService;

    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return loginService.login(adminLoginDTO);
    }

    @GetMapping("/studentwithCompanyStatus")
    public Page<PlacedLeaderBoardDTO> getStudentsandCompany(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) ApplicationStatus status,
            Pageable pageable) {

        System.out.println("Status Value:"+status);

        return jobAppllicationService.getStudentsandCompany(keyword, company,status,pageable);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return adminServices.getDashboard();
    }


    @GetMapping("/students")
    public List<?> getStudents() {
        return adminServices.getAllStudentsWithApplications();
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


    @GetMapping("/getAllcompanies")
    public List<String> getCompanies() {
        return jobService.getAllComapnies();
    }

    @GetMapping("/export/students")
    public ResponseEntity<InputStreamResource> exportStudentsData(
            @RequestParam(defaultValue = "all") String type) throws Exception {

        ByteArrayInputStream file = exportService.exportStudentsData(type);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(file));
    }
}