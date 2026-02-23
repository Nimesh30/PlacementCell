package com.Project.PlacementCell.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobsDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String companyName;
        private String jobTitle;
        private Double packageLpa;
        private String location;
        private LocalDate deadline;
        private Double minCgpa;

        @Column(length = 5000)
        private String description;

        @Column(length = 2000)
        private String trainingDetails;

        @Column(length = 2000)
        private String growthPath;

        private String eligibleDegrees; // B.Tech,BE,B.Sc

        private Boolean active = true;
}
