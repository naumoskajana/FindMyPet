package com.example.findmypet.service;

import com.example.findmypet.dto.UserChangeDTO;
import com.example.findmypet.dto.UserDTO;
import com.example.findmypet.dto.UserLoginDTO;
import com.example.findmypet.dto.UserRegistrationDTO;
import com.example.findmypet.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(UserRegistrationDTO userRegistrationDTO);
    void confirmRegistration(String token);
    void login(UserLoginDTO userLoginDTO);
    User findByEmail(String email);
    void changeUserDetails(User user, UserChangeDTO userChangeDTO);
    void resetPasswordLink(String email);
    void resetPassword(String token, String newPassword);
    UserDTO getLoggedInUser(String token);
}
