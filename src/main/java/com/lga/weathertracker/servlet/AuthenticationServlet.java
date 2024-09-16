package com.lga.weathertracker.servlet;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.UserService;
import com.lga.weathertracker.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (sessionService.isSessionIdValid(value)) {
                        User user = sessionService.getSessionUser(value);
                        webContext.setVariable("login", user.getLogin());
                        webContext.setVariable("password", user.getPassword());
                    }
                }
            }
        }

        ThymeleafUtil.sendPage("auth",
                webContext,
                resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (!userService.verifyExistence(login)) {
            webContext.setVariable("userNotExist", true);
            ThymeleafUtil.sendPage("auth", webContext, resp.getWriter());
            return;
        }

        if (!userService.verifyCredentials(login, password)) {
            webContext.setVariable("userNotExist", false);
            webContext.setVariable("invalidPassword", true);
            ThymeleafUtil.sendPage("auth", webContext, resp.getWriter());
            return;
        }

        User user = userService.findUserByLogin(login);
        UUID uuid = UUID.randomUUID();
        sessionService.saveNewSession(uuid,user);
        Cookie sessionId = new Cookie("sessionId", uuid.toString());
        resp.addCookie(sessionId);


//        req.getRequestDispatcher("/").forward(req,resp);
        resp.sendRedirect("/");
    }
}
