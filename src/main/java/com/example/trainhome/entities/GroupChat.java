package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_chat")
@NoArgsConstructor
public class GroupChat {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person personId;

    @Column(name = "name", nullable = false)
    private String name;

    public GroupChat(Person personId, String name) {
        this.personId = personId;
        this.name = name;
    }
}
