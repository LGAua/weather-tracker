package com.lga.weathertracker.servlet;

import com.lga.weathertracker.entity.User;
import com.lga.weathertracker.model.ForecastDto;
import com.lga.weathertracker.model.LocationPositionDto;
import com.lga.weathertracker.model.WeatherApiResponseDto;
import com.lga.weathertracker.service.SessionService;
import com.lga.weathertracker.service.WeatherApiService;
import com.lga.weathertracker.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    private final SessionService sessionService = new SessionService();
    private final WeatherApiService weatherService = new WeatherApiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());
        webContext.setVariable("auth",false);

        //todo create class session- validator for checking cookies
        //if (SessionValidator.isValid(session){ put var in webContext })
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (sessionService.isSessionIdValid(value)) {
                        User user = sessionService.getSessionUser(value);
                        webContext.setVariable("auth",true);
                        webContext.setVariable("username", user.getLogin());
                    }
                }
            }
        }

        ThymeleafUtil.sendPage("home", webContext, resp.getWriter());
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);


        WebContext webContext = ThymeleafUtil.getWebContext(req, resp, getServletContext());
        ThymeleafUtil.sendPage("home",
                webContext,
                resp.getWriter());
    }
}
