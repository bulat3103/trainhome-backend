package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sport_sphere")
public class SportSphere {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
