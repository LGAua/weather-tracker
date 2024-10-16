package com.lga.weathertracker.controller;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
    public String authentication() {
        return "sign-in";
    }

    @PostMapping
    public String verifyCredentials(@ModelAttribute @Validated User user,
                                    BindingResult bindingResult,
                                    RedirectAttributes attributes,
                                    Model model,
                                    HttpServletResponse resp) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("bindingErrors", bindingResult.getFieldErrors());
            return "redirect:/sign-in";
        }

        if (userService.verifyCredentials(user)) {
            UUID uuid = sessionService.saveNewSession(user);
            resp.addCookie(new Cookie("sessionId", uuid.toString()));

            return "redirect:/home";
        }
        model.addAttribute("error", INVALID_CREDENTIALS);
        return "sign-in";
    }
}

