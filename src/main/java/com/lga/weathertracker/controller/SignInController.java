package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;
import java.util.UUID;

@Controller
@RequestMapping("/sign-in")
public class SignInController {

    private static final String INVALID_CREDENTIALS = "Wrong e-mail or password";
    private final UserService userService;
    private final SessionService sessionService;


    public SignInController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String authentication(){
        return "sign-in";
    }

    @PostMapping
    public String verifyCredentials(@ModelAttribute User user, Model model, HttpServletResponse resp){
        if(userService.verifyCredentials(user)){
            UUID uuid = sessionService.saveNewSession(user);
            resp.addCookie(new Cookie("sessionId",uuid.toString()));

            return "redirect:/home";
        }
        model.addAttribute("error", INVALID_CREDENTIALS);
        return "sign-in";
    }
}

