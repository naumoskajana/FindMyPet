package com.example.findmypet.service.implementation;

import com.example.findmypet.dto.UserChangeDTO;
import com.example.findmypet.dto.UserLoginDTO;
import com.example.findmypet.dto.UserRegistrationDTO;
import com.example.findmypet.enumeration.UserType;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.exceptions.ExistingPasswordException;
import com.example.findmypet.exceptions.UserAlreadyExistsException;
import com.example.findmypet.exceptions.TokenExpiredException;
import com.example.findmypet.exceptions.UserHasInactiveAccountException;
import com.example.findmypet.repository.UserRepository;
import com.example.findmypet.config.security.JwtUtils;
import com.example.findmypet.service.EmailService;
import com.example.findmypet.service.LostPetService;
import com.example.findmypet.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final LostPetService lostPetService;
    private final JwtUtils jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService,
                           @Lazy LostPetService lostPetService,
                           JwtUtils jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.lostPetService = lostPetService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void register(UserRegistrationDTO userRegistrationDTO) {
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());

        if (existingUser != null){
            throw new UserAlreadyExistsException(userRegistrationDTO.getEmail());
        }

        User newUser = new User();
        newUser.setFirstName(userRegistrationDTO.getFullName().split(" ")[0]);
        newUser.setLastName(userRegistrationDTO.getFullName().split(" ")[1]);
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        newUser.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        newUser.setUserType(UserType.USER);
        newUser.setActivated(false);
        userRepository.save(newUser);

        String token = jwtTokenUtil.generateJwtToken(new UsernamePasswordAuthenticationToken(newUser, null));
        String confirmationLink = "Ве молиме кликнете на следниот линк за активација на профилот: http://localhost:8080/api/token/profile-activation-template?token=" + token;
        emailService.sendMessage(userRegistrationDTO.getEmail(), "Активација на профил", confirmationLink);
    }

    @Override
    public void confirmRegistration(String token) {
        if (!jwtTokenUtil.validateJwtToken(token)){
            throw new TokenExpiredException(token);
        }
        String email = jwtTokenUtil.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(email);
        user.setActivated(true);
        userRepository.save(user);
    }

    @Override
    public void login(UserLoginDTO userLoginDTO) {
        User user = findByEmail(userLoginDTO.getEmail());

        if (!user.getActivated()){
            throw new UserHasInactiveAccountException(user.getEmail());
        }

        user.setDeviceToken(userLoginDTO.getDeviceToken());

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void changeUserDetails(User user, UserChangeDTO userChangeDTO) {
        if (userChangeDTO.getNewPassword() != null) {
            if (passwordEncoder.encode(user.getPassword()).equals(passwordEncoder.encode(userChangeDTO.getNewPassword()))) {
                throw new ExistingPasswordException();
            }

            user.setPassword(passwordEncoder.encode(userChangeDTO.getNewPassword()));
        }

        if (userChangeDTO.getFullName() != null){
            String[] fullNameSplitted = userChangeDTO.getFullName().split("\\s+");
            String firstName = fullNameSplitted[0];
            String lastName = fullNameSplitted.length > 1 ? fullNameSplitted[1] : "";
            user.setFirstName(firstName);
            user.setLastName(lastName);
        }

        if (userChangeDTO.getPhoneNumber() != null){
            user.setPhoneNumber(userChangeDTO.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public void resetPasswordLink(String email) {
        String token = jwtTokenUtil.generateJwtToken(new UsernamePasswordAuthenticationToken(findByEmail(email), null));
        String resetPasswordLink = "Ве молиме кликнете на линкот за промена на вашата лозинка: http://localhost:8080/api/token/reset-password-template?token=" + token;
        emailService.sendMessage(email, "Промена на лозинка", resetPasswordLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        if (!jwtTokenUtil.validateJwtToken(token)){
            throw new TokenExpiredException(token);
        }
        String email = jwtTokenUtil.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        lostPetService.deleteLostPetsByUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
