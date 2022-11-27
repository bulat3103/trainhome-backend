package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "session")
@NoArgsConstructor
public class Session {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    public Session(Person person, String token, Date createdAt, boolean expired) {
        this.person = person;
        this.token = token;
        this.createdAt = createdAt;
        this.expired = expired;
    }
}
