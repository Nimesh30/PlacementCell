package com.Project.PlacementCell.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminid;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String mobile;

    @Column(nullable = false)
    private boolean active = true;

}