package com.Project.PlacementCell.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StatusUpdateDTO {
    private List<Long> ids;
    private String status;
}
