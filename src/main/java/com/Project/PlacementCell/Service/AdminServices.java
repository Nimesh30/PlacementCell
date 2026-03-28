
package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.AllStudentsDTO;
import com.Project.PlacementCell.DTO.AdminDTO.CompanyWiseHiringDTO;
import com.Project.PlacementCell.DTO.AdminDTO.DashboardDTO;
//import com.Project.PlacementCell.Repository.ApplicationRepository;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
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




import java.util.List;

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

    public ResponseEntity<?> getStudents() {

        List<AllStudentsDTO> studentsList = adminRepository.getAllStudents();
        return ResponseEntity.ok(studentsList);
    }
}