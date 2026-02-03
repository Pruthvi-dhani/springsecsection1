package com.udemycourse.springsecsection1.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component("customAuthorizationEntry")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authorization failed");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "HttpStatus.UNAUTHORIZED.getReasonPhrase()");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Unauthorized");
        body.put("message", "Authentication is required to access this resource.");
        body.put("path", request.getRequestURI());

        // write JSON to response
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
