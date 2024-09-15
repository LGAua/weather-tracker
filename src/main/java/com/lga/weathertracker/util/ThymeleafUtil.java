package com.lga.weathertracker.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.Writer;

public class ThymeleafUtil {
    private static JakartaServletWebApplication webApplication;

    public static void sendPage(String pageName, WebContext context, Writer writer) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(getTemplateResolver());

        templateEngine.process(pageName, context, writer);
    }

    public static WebContext getWebContext(HttpServletRequest req, HttpServletResponse resp, ServletContext servletContext){
        webApplication = JakartaServletWebApplication.buildApplication(servletContext);
        return new WebContext(webApplication.buildExchange(req, resp));
    }

    private static WebApplicationTemplateResolver getTemplateResolver() {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(webApplication);

        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }
}
