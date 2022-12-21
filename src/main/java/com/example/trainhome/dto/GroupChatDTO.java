package com.example.trainhome.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupChatDTO {
    Long id;
    PersonDTO personDTO;
    String name;
    Long createPersonId;
}
