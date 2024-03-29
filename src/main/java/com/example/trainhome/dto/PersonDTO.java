package com.example.trainhome.dto;

import com.example.trainhome.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private ImageDTO image;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private boolean sex;

    public static PersonDTO PersonToPersonDTO(Person person){
        return new PersonDTO(person.getId(), person.getName(), new ImageDTO(person.getImage().getId(), person.getImage().getHex()),
                person.getPhoneNumber(), person.getEmail(), person.getBirthday(), person.isSex());
    }
}
