package com.example.trainhome.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PersonDTO {
    private String name;
    private String image;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private boolean sex;
}
