package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.DashboardDTO;
//import com.Project.PlacementCell.Repository.ApplicationRepository;
import com.Project.PlacementCell.Repository.JobRepository;
import com.Project.PlacementCell.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;
//    @Autowired
//    private ApplicationRepository applicationRepository;

    public ResponseEntity<DashboardDTO> getDashboard() {

        long totalStudents = studentRepository.count();
        long jobsPosted = jobRepository.count();
        long placedStudents = studentRepository.countByPlacedTrue();
//        long applications = applicationRepository.count();

        DashboardDTO stats = new DashboardDTO(
                totalStudents,
                jobsPosted,
                placedStudents
//                applications

        );

        return ResponseEntity.ok(stats);
    }
}