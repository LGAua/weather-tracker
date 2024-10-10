package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sign-up")
public class SighUpController {

    private static final String USER_ALREADY_EXISTS = "User already exists";

    private final UserService userService;

    public SighUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(){
        return "sign-up";
    }

    @PostMapping
    public String verifyCredentials(@ModelAttribute User user, Model model){
        if (userService.verifyExistence(user.getLogin())) {
            model.addAttribute("error", USER_ALREADY_EXISTS);
            return "sign-up";
        }
        userService.saveNewUser(user);
        return "redirect:/sign-in";
    }
}
