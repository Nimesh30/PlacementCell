package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.DashboardDTO;
//import com.Project.PlacementCell.Repository.ApplicationRepository;
import com.Project.PlacementCell.DTO.AdminDTO.PlacedLeaderBoardDTO;
import com.Project.PlacementCell.Repository.JobApplicationsRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobApplicationsRepository jobApplicationsRepository;

    public ResponseEntity<DashboardDTO> getDashboard() {

        long totalStudents = studentRepository.count();
        long jobsPosted = jobRepository.count();
        long placedStudents = studentRepository.countByPlacedTrue();
        long applications = jobApplicationsRepository.count();

        //Placement LeaderBoard

        List<PlacedLeaderBoardDTO> placedLeaderBoard =
                jobApplicationsRepository.getPlacedLeaderBoard();
//        long companyName = jobApplicationsRepository.count();
//        long packageAmount = jobApplicationsRepository.count();

//        System.out.println("List: " + studentName);
        DashboardDTO stats = new DashboardDTO(
                totalStudents,
                jobsPosted,
                placedStudents,
                applications,
                placedLeaderBoard
        );

        System.out.println("stats: " + stats);
        return ResponseEntity.ok(stats);
    }
}