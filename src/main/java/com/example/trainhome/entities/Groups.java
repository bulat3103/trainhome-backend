package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "groups")
@NoArgsConstructor
public class Groups {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id")
    private Coach coachId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sport_sphere_id")
    private SportSphere sportSphereId;

    @Column(name = "max_count", nullable = false)
    private Integer maxCount;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "trains_left", nullable = false)
    private Integer trainsLeft;

    public Groups(String name, Coach coachId, SportSphere sportSphereId, Integer maxCount, Integer count, Integer trainsLeft) {
        this.name = name;
        this.coachId = coachId;
        this.sportSphereId = sportSphereId;
        this.maxCount = maxCount;
        this.count = count;
        this.trainsLeft = trainsLeft;
    }
}
