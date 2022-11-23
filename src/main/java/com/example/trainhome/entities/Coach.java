package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "person")
public class Coach {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
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
