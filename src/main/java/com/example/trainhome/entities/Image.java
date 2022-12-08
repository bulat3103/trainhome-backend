package com.example.trainhome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table(name = "image")
@NoArgsConstructor
public class Image {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hex", nullable = false)
    private String hex;

    public Image(Long id, String hex) {
        this.id = id;
        this.hex = hex;
    }
}
