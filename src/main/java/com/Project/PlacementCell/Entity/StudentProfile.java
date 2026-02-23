package com.Project.PlacementCell.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Integer passingYear;
    private String imageUrl;
    private String ImagePublicId;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

}