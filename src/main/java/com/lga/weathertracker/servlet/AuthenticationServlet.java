package com.lga.weathertracker.servlet;

import com.lga.weathertracker.service.UserService;
import com.lga.weathertracker.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.IOException;

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ThymeleafUtil.sendPage("auth",
                ThymeleafUtil.getWebContext(req, resp, getServletContext()),
                resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!userService.verifyExistence(login)){
            WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());

            webContext.setVariable("userNotExist",true);

            ThymeleafUtil.sendPage("auth",webContext,resp.getWriter());
            return;
        }

        if (!userService.verifyCredentials(login,password)) {
            WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());

            webContext.setVariable("userNotExist",false);
            webContext.setVariable("invalidPassword",true);
            ThymeleafUtil.sendPage("auth",webContext,resp.getWriter());
            return;
        }

        resp.sendRedirect("/home");
    }
}
