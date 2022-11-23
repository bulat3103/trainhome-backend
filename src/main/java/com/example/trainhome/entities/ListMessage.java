package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "list_message")
public class ListMessage {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private GroupChat chatId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person personId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date_create", nullable = false)
    private Date dateCreate;
}
