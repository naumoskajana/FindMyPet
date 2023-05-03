package com.example.findmypet.service;

import com.example.findmypet.dto.UserChangeDTO;
import com.example.findmypet.dto.UserRegistrationDTO;
import com.example.findmypet.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegistrationDTO userRegistrationDTO);
    void confirmRegistration(String token);
    User findByEmail(String email);
    void changeUserDetails(UserChangeDTO userChangeDTO);
    void resetPasswordLink(String email);
    void resetPassword(String token, String newPassword);
}
