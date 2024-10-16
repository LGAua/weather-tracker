package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.UserService;
import com.password4j.Hash;
import com.password4j.HashBuilder;
import com.password4j.Password;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.password4j.Password.hash;

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
    public String verifyCredentials(@ModelAttribute @Validated User user,
                                    BindingResult bindingResult,
                                    RedirectAttributes attributes,
                                    Model model){
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("bindingErrors", bindingResult.getFieldErrors());
            return "redirect:/sign-up";
        }

        if (userService.verifyExistence(user.getLogin())) {
            model.addAttribute("error", USER_ALREADY_EXISTS);
            return "sign-up";
        }

        Hash hash = hash(user.getPassword()).withBcrypt();
        user.setPassword(hash.getResult());
        userService.saveNewUser(user);
        return "redirect:/sign-in";
    }
}
