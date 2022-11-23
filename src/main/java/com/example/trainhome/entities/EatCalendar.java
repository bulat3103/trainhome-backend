package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "eat_calendar")
@NoArgsConstructor
public class EatCalendar {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "info")
    private String info;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person personId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Coach coachId;

    public EatCalendar(String info, Date date, Person personId, Coach coachId) {
        this.info = info;
        this.date = date;
        this.personId = personId;
        this.coachId = coachId;
    }
}
