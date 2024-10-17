package com.lga.weathertracker.config;

import com.lga.weathertracker.interceptor.SessionAccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionAccessInterceptorConfig implements WebMvcConfigurer {

    private final SessionAccessInterceptor sessionAccessInterceptor;

    public SessionAccessInterceptorConfig(SessionAccessInterceptor sessionAccessInterceptor) {
        this.sessionAccessInterceptor = sessionAccessInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionAccessInterceptor)
                .addPathPatterns("/home","/search");
    }
}
