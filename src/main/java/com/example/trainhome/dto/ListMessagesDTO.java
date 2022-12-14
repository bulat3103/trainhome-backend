package com.example.trainhome.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ListMessagesDTO {
    private Long id;
    private Long groupChatId;
    private PersonDTO personDTO;
    private String content;
    private Date dateCreate;
}
