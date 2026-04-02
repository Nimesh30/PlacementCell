package com.Project.PlacementCell.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @Column(unique = true, nullable = false)
    private String studentId;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean firstLogin = true;

    @Column(name = "placed")
    private Boolean placed= false;

    // 🔥 NOW Student OWNS the relationship
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private StudentProfile profile;

    // One-to-Many remains same
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<JobApplications> applications;
}
