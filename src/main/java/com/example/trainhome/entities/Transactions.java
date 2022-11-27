package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
public class Transactions {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id")
    private Coach coachId;

    @Column(name = "money", nullable = false)
    private int money;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

    public Transactions(Date date, Coach coachId, int money, Person personId) {
        this.date = date;
        this.coachId = coachId;
        this.money = money;
        this.personId = personId;
    }
}
