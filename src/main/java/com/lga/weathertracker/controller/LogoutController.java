package com.lga.weathertracker.controller;

import com.lga.weathertracker.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final SessionService sessionService;

    public LogoutController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String terminateSession(HttpServletRequest req, HttpServletResponse resp){
        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("sessionId"))
                .findFirst()
                .ifPresent(cookie -> {
                    sessionService.invalidateSession(cookie.getValue());
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                });

        return "redirect:/sign-in";
    }
}
