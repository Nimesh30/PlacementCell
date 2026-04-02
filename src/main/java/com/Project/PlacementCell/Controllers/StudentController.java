package com.Project.PlacementCell.Controllers;

//import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.CompanyDTO;
import com.Project.PlacementCell.DTO.OfferDTO;
import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.Entity.JobApplications;
import com.Project.PlacementCell.Entity.StudentProfile;
import com.Project.PlacementCell.Service.ExportService;
import com.Project.PlacementCell.Service.JobAppllicationService;
import com.Project.PlacementCell.Service.JobService;
import com.Project.PlacementCell.Service.StudentService;
import com.Project.PlacementCell.enums.ApplicationStatus;
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
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private JobAppllicationService jobAppllicationService;

    @Autowired
    private JobService jobService;

//    @PostMapping("/add")
//    public ResponseEntity<StudentProfile> addStudent(@ModelAttribute StudentProfile studProfile,
//                                                     @RequestParam("file") MultipartFile file,
//                                                     @RequestParam("studentId") String studentId) throws IOException {
//
//        System.out.println("In Add student Controller");
//        StudentProfile savedProfile =
//                studentService.addStudent(studProfile, file, studentId);
//
//        return ResponseEntity.ok(savedProfile);
//    }

    // ✅ ADD PROFILE
    @PostMapping("/add/{studentId}")
    public ResponseEntity<?> addProfile(
            @PathVariable String studentId,
            @RequestPart("profile") StudentProfile profile,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            return ResponseEntity.ok(
                    studentService.addStudent(profile, file, studentId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
//    @GetMapping("/profile/{studentId}")
//    public ResponseEntity<StudentProfileResponse> getProfile(
//            @PathVariable String studentId
//    ) {
//        return ResponseEntity.ok(
//                studentService.getProfileByStudentId(studentId)
//        );
//    }

    // ✅ GET PROFILE
    @GetMapping("/profile/{studentId}")
    public ResponseEntity<?> getProfile(@PathVariable String studentId) {
        return ResponseEntity.ok(
                studentService.getProfileByStudentId(studentId)
        );
    }




//    @GetMapping("/studentwithCompanyStatus")
//    public Page<PlacedLeaderBoardDTO> getStudentsandCompany(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) String company,
//            @RequestParam(required = false) ApplicationStatus status,
//            Pageable pageable) {
//
//        System.out.println("Status Value:"+status);
//
//        return jobAppllicationService.getStudentsandCompany(keyword, company,status,pageable);
//    }

    @GetMapping("/getAllcompanies")
    public List<String> getCompanies() {
        return jobService.getAllComapnies();
    }

    // 🔢 Count API
    @GetMapping("/selected-count/{studentId}")
    public long getSelectedCount(@PathVariable String studentId) {
        return jobAppllicationService.getSelectedCount(studentId);
    }

    @GetMapping("/selected-offers/{studentId}")
    public List<OfferDTO> getSelectedOffers(@PathVariable String studentId) {
        return jobAppllicationService.getSelectedOffers(studentId);
    }

    @PutMapping("/update-response")
    public void updateResponse(@RequestBody Map<String, Object> payload) {
        Long id = Long.valueOf(payload.get("id").toString());
        String response = payload.get("response").toString();

        jobAppllicationService.updateStudentResponse(id, response);
    }

}

