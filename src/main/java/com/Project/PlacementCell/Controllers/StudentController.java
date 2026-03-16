package com.Project.PlacementCell.Controllers;

//import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.CompanyDTO;
import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.ExportService;
import com.Project.PlacementCell.Service.JobAppllicationService;
import com.Project.PlacementCell.Service.JobService;
import com.Project.PlacementCell.Service.StudentService;
import jakarta.annotation.security.RolesAllowed;
import org.apache.catalina.LifecycleState;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Pageable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private JobAppllicationService jobAppllicationService;

    @Autowired
    private JobService jobService;

    @PostMapping("/add")
    public ResponseEntity<StudentProfile> addStudent(@ModelAttribute StudentProfile studProfile,
                                                     @RequestParam("file") MultipartFile file,
                                                     @RequestParam("studentId") String studentId) throws IOException {

        System.out.println("In Add student Controller");
        StudentProfile savedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/profile/{studentId}")
    public ResponseEntity<StudentProfileResponse> getProfile(
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(
                studentService.getProfileByStudentId(studentId)
        );
    }

    @PatchMapping("/update/{studentId}")
    public ResponseEntity<StudentProfile> updateStudent(
            @ModelAttribute StudentProfile studProfile,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable String studentId) throws IOException {

        StudentProfile updatedProfile =
                studentService.addStudent(studProfile, file, studentId);

        return ResponseEntity.ok(updatedProfile);
    }


    @GetMapping("/studentwithCompanyStatus")
    public Page<PlacedLeaderBoardDTO> getStudentsandCompany(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String company,
            Pageable pageable) {

        return jobAppllicationService.getStudentsandCompany(keyword, company, pageable);
    }

    @GetMapping("/getAllcompanies")
    public List<String> getCompanies() {
        return jobService.getAllComapnies();
    }

}

