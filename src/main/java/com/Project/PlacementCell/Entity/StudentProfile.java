package com.Project.PlacementCell.Entity;

import com.Project.PlacementCell.enums.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;
    private String personalEmail;
    private String mobileNumber;

    private Double tenthMarks;
    private Double twelfthMarks;
    private String stream;

    private Double bachelorsCgpa;
    private Double mastersCgpa;

    private String institute;
    private String department;

    @Enumerated(EnumType.STRING)
    private Branch branch = Branch.Select;

    private Integer passingYear;
    private String imageUrl;
    private String imagePublicId;

    // 🔥 NOW inverse side
    @JsonIgnore
    @OneToOne(mappedBy = "profile")
    private Student student;
}