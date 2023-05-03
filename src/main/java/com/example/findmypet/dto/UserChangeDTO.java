package com.example.findmypet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeDTO {

    private String email;
    private String newPassword;
    private String fullName;
    private String phoneNumber;

}
