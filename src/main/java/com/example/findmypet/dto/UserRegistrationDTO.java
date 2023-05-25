package com.example.findmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

}
