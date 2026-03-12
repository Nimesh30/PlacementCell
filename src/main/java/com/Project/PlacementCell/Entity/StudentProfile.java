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
//    private String studentId;
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
    @NonNull
    private Branch branch = Branch.Select;
    private Integer passingYear;
    private String imageUrl;
    private String imagePublicId;

    // Foreign Key
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<JobApplications> applications;

}