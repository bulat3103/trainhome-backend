package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "training")
@NoArgsConstructor
public class Training {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_date", nullable = false, unique = true)
    private Date trainingDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id")
    private Coach coachId;

    @Column(name = "link", nullable = false)
    private String link;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Groups groupId;

    public Training(Date trainingDate, Coach coachId, String link, Groups groupId) {
        this.trainingDate = trainingDate;
        this.coachId = coachId;
        this.link = link;
        this.groupId = groupId;
    }
}
