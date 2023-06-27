package com.example.findmypet.web;

import com.example.findmypet.dto.UserChangeDTO;
import com.example.findmypet.dto.UserDTO;
import com.example.findmypet.dto.UserLoginDTO;
import com.example.findmypet.dto.UserRegistrationDTO;
import com.example.findmypet.config.security.JwtUtils;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.exceptions.*;
import com.example.findmypet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
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
    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
        try {
            userService.confirmRegistration(token);
            return ResponseEntity.ok("User activated successfully.");
        } catch (TokenExpiredException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            userService.login(userLoginDTO);
        } catch (UserHasInactiveAccountException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtUtils.generateJwtToken(authentication));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(jwtUtils.validateJwtToken(token));
    }

    @PostMapping("/change-user-details")
    public ResponseEntity<String> changeUserDetails(@RequestBody UserChangeDTO userChangeDTO) {
        try {
             Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
             User user = (User) authentication.getPrincipal();
            userService.changeUserDetails(user, userChangeDTO);
            return ResponseEntity.ok("User edited successfully.");
        } catch (ExistingPasswordException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/reset-password")
    public void resetPasswordLink(@RequestParam String email){
        userService.resetPasswordLink(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String password){
        try {
            userService.resetPassword(token, password);
            return ResponseEntity.ok("User password reset successfully.");
        } catch (TokenExpiredException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getLoggedInUser(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String token = "";

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            token = headerAuth.substring(7);
        }

        String email = jwtUtils.getEmailFromJwtToken(token);
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(user.getAsUserDTO());
    }

}
