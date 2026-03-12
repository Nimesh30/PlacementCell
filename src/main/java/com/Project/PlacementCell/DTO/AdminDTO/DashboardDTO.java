package com.Project.PlacementCell.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardDTO {
    private long totalStudents;
    private long jobsPosted;
    private long placedStudents;
    private long application;
    //PlacedLeaderDTO which is created for Placement LeaderBoard
    private List<PlacedLeaderBoardDTO> placedLeaderBoard;
}