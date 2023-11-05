package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "credit")
@NoArgsConstructor
public class Credit {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payed_months", nullable = false)
    private int payedMonths;

    @Column(name = "common_count_months", nullable = false)
    private int commonCountMonths;

    @Column(name = "next_pay", nullable = false)
    private Date nextPay;

    @Column(name = "money", nullable = false)
    private int money;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id")
    private Coach coachId;

    public Credit(int payedMonths, int commonCountMonths, Date nextPay, int money, Person personId, Coach coachId) {
        this.payedMonths = payedMonths;
        this.commonCountMonths = commonCountMonths;
        this.nextPay = nextPay;
        this.money = money;
        this.personId = personId;
        this.coachId = coachId;
    }
}
