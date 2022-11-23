package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Coach coachId;

    @Column(name = "money", nullable = false)
    private int money;
}
