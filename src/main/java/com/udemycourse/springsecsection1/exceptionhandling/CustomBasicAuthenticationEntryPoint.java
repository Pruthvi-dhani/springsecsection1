package com.udemycourse.springsecsection1.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component("customAuthEntry")
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authentication failed");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), "HttpStatus.UNAUTHORIZED.getReasonPhrase()");
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", 401);
        body.put("error", "Unauthorized");
        body.put("message", "Authentication is required to access this resource.");
        body.put("path", request.getRequestURI());

        // write JSON to response
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
