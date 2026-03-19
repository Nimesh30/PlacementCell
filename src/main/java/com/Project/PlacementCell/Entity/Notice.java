package com.Project.PlacementCell.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String message;

    private String fileUrl; // optional

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean pinned = false;
}