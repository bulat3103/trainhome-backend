package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_chat")
public class GroupChat {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person personId;

    @Column(name = "name", nullable = false)
    private String name;
}
