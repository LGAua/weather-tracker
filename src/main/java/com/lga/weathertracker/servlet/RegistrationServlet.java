package com.lga.weathertracker.servlet;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.UserService;
import com.lga.weathertracker.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ThymeleafUtil.sendPage("registration",
                ThymeleafUtil.getWebContext(req, resp, getServletContext()),
                resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (userService.verifyExistence(login)){
            WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());

            webContext.setVariable("userExist","user with such email already exist");

            ThymeleafUtil.sendPage("registration",webContext,resp.getWriter());
            return;
        }
        User user = User.builder()
                .login(login)
                .password(password)
                .build();

        userService.saveNewUser(user);

        resp.sendRedirect("/auth");
    }
}
