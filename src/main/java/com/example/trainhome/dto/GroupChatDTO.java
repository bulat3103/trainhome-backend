package com.example.trainhome.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupChatDTO {
    Long id;
    PersonDTO personDTO;
    String name;
}
