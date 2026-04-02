
package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO;
import com.Project.PlacementCell.DTO.AdminDTO.CompanyWiseHiringDTO;
import com.Project.PlacementCell.DTO.AdminDTO.DashboardDTO;
//import com.Project.PlacementCell.Repository.ApplicationRepository;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.DTO.ApplicationDTO;
import com.Project.PlacementCell.DTO.StudentDTO.StudentProfileResponse;
import com.Project.PlacementCell.DTO.StudentProfileDTO;
import com.Project.PlacementCell.Entity.Student;
import com.Project.PlacementCell.Repository.AdminRepository;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import com.Project.PlacementCell.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServices {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationsRepository jobApplicationsRepository;

    public ResponseEntity<DashboardDTO> getDashboard() {

        long totalStudents = studentRepository.count();
        long jobsPosted = jobRepository.count();
        long placedStudents = jobApplicationsRepository.countByStatus(ApplicationStatus.SELECTED);
        long applications = jobApplicationsRepository.count();

        //Placement LeaderBoard
        //Pageable will retrieve only top 5 records...
//        Pageable topFive = PageRequest.of(0, 5);
        Pageable topFive =PageRequest.of(0,5);
        List<PlacedLeaderBoardDTO> placedLeaderBoard =
                jobApplicationsRepository.getPlacedLeaderBoard(topFive);
        List<CompanyWiseHiringDTO> companywiseHiring =
                jobApplicationsRepository.getCompanyHiringStats();
        DashboardDTO stats = new DashboardDTO(
                totalStudents,
                jobsPosted,
                placedStudents,
                applications,
                placedLeaderBoard,
                companywiseHiring
        );

        System.out.println("stats: " + stats);
        return ResponseEntity.ok(stats);
    }


    public List<AllStudentsDTO> getAllStudentsWithApplications() {

        List<AllStudentsDTO> list = adminRepository.getAllStudents();

        Map<String, AllStudentsDTO> map = new LinkedHashMap<>();

        for (AllStudentsDTO dto : list) {

            AllStudentsDTO student = map.get(dto.getStudentId());

            // ✅ Create student once
            if (student == null) {

                student = new AllStudentsDTO();

                student.setUsername(dto.getUsername());
                student.setStudentId(dto.getStudentId());
                student.setEmail(dto.getEmail());
                student.setPlaced(dto.getPlaced());

                // ✅ FIX: use direct fields (NOT dto.getProfile())
                StudentProfileDTO profile = new StudentProfileDTO(
                        dto.getFullName(),
                        dto.getMobileNumber(),
                        dto.getTenthMarks(),
                        dto.getTwelfthMarks(),
                        dto.getBachelorsCgpa(),
                        dto.getDepartment(),
                        dto.getPassingYear()
                );

                student.setProfile(profile);

                // ✅ VERY IMPORTANT (init list)
                student.setApplications(new ArrayList<>());

                map.put(dto.getStudentId(), student);
            }

            // ✅ FIX: use dto.getCompanyName() directly
            if (dto.getCompanyName() != null) {

                ApplicationDTO app = new ApplicationDTO();
                app.setCompanyName(dto.getCompanyName());
                app.setStatus(dto.getStatus());

                student.getApplications().add(app);
            }
        }

        return new ArrayList<>(map.values());
    }
}