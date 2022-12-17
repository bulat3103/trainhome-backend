package com.example.trainhome.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "sex", nullable = false)
    private boolean sex;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role roleId;
}
