package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "coach")
public class Coach {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "info")
    private String info;

    @Column(name = "money", nullable = false)
    private int money;
}
