package com.example.trainhome.dto;

import com.example.trainhome.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String password;
    private String name;
    private ImageDTO image;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private boolean sex;
    private String achievements;
    private String info;
    private List<SportPriceDTO> listPrices;
}
