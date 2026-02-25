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
    private Long id;

    private String username;
    @Column(unique = true,nullable = false)
    private String studentId;
//    private String studentid;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean firstLogin = true;   // force change password
}
