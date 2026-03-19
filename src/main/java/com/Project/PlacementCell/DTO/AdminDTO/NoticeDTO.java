package com.Project.PlacementCell.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long id;
    private String title;
    private String message;
    private String fileUrl;
    private Boolean pinned;
    private LocalDateTime createdAt;
}