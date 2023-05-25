package com.example.findmypet.web;

import com.example.findmypet.dto.UserChangeDTO;
import com.example.findmypet.dto.UserDTO;
import com.example.findmypet.dto.UserLoginDTO;
import com.example.findmypet.dto.UserRegistrationDTO;
import com.example.findmypet.config.security.CustomEmailAndPasswordProvider;
import com.example.findmypet.config.security.JwtUtils;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.exceptions.UserAlreadyExistsException;
import com.example.findmypet.exceptions.UserHasInactiveAccountException;
import com.example.findmypet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CustomEmailAndPasswordProvider customEmailAndPasswordProvider;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, CustomEmailAndPasswordProvider customEmailAndPasswordProvider, JwtUtils jwtUtils) {
        this.userService = userService;
        this.customEmailAndPasswordProvider = customEmailAndPasswordProvider;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            userService.register(userRegistrationDTO);
            return ResponseEntity.ok("User registered successfully.");
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping("/activate")
    public void activateUser(@RequestParam("token") String token) {
        userService.confirmRegistration(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            userService.login(userLoginDTO);
        } catch (UserHasInactiveAccountException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        Authentication authentication = customEmailAndPasswordProvider.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtUtils.generateJwtToken(authentication));
    }

    @GetMapping("/validate-token")
    public Boolean validateToken(@RequestParam String token) {
        return jwtUtils.validateJwtToken(token);
    }

    @PostMapping("/change-user-details")
    public void changeUserDetails(@RequestBody UserChangeDTO userChangeDTO) {
        userService.changeUserDetails(userChangeDTO);
    }

    @GetMapping("/reset-password")
    public void resetPasswordLink(@RequestParam String email){
        userService.resetPasswordLink(email);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestParam String token, @RequestParam String password){
        userService.resetPassword(token, password);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user.getAsUserDTO());
    }

}
