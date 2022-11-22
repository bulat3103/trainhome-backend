package com.example.trainhome.userservice.dto;

import com.example.trainhome.userservice.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private String image;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private boolean sex;

    public static PersonDTO PersonToPersonDTO(Person person){
        return new PersonDTO(person.getId(), person.getName(), person.getImage(),
                person.getPhoneNumber(), person.getEmail(), person.getBirthday(), person.isSex());
    }
}
