package com.example.findmypet.web;

import com.example.findmypet.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/token")
public class TokenController {

    @GetMapping("/profile-activation-template")
    public String activateUser(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "activate-account";
    }

    @GetMapping("/reset-password-template")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

}
