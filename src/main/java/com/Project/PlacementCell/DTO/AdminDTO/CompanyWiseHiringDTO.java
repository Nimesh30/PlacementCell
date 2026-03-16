package com.Project.PlacementCell.DTO.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyWiseHiringDTO {

    private String companyName;
    private Long hiringCount;
    private Double packageLpa;
}
