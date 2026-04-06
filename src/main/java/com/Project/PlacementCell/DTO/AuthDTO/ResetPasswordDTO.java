package com.Project.PlacementCell.DTO.AuthDTO;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String token;
    private String newPassword;

}