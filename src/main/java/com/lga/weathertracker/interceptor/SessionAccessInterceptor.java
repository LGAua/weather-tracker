package com.lga.weathertracker.interceptor;

import com.lga.weathertracker.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionAccessInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;

    public SessionAccessInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!sessionService.findUserByRequestSessionId(request).isPresent()) {
            request.getRequestDispatcher("/not-authorised").forward(request, response);
            return false;
        }
        return true;
    }
}
