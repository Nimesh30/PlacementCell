package com.Project.PlacementCell.DTO;

import com.Project.PlacementCell.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private String companyName;   // ✅ FIXED
    private ApplicationStatus status;
}
