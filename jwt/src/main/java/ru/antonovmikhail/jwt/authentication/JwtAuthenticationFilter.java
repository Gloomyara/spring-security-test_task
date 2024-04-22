package ru.antonovmikhail.jwt.authentication;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import static org.springframework.security.oauth2.jwt.Jwt.withTokenValue;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);

        if (token != null && validateToken(token)) {
            Authentication authentication = createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) throws JsonProcessingException {
        // Логика извлечения токена из запроса (например, из заголовка Authorization)
        HashMap<String, String> params = new ObjectMapper().readValue(request.getHeader("Authorization"), HashMap.class);
        return params.get("token");
    }

    private boolean validateToken(String token) throws JsonProcessingException {
        // Логика верификации токена
        return JwtUtil.validateToken(token, extractUserDetailsFromToken(token));
    }

    private Authentication createAuthentication(String token) throws JsonProcessingException {
        // Получение информации о пользователе из токена
        UserDetails userDetails = extractUserDetailsFromToken(token);
        // Создание объекта Authentication
        return new JwtAuthenticationToken(withTokenValue(token).build(), userDetails.getAuthorities(), userDetails.getUsername());
    }

    private UserDetails extractUserDetailsFromToken(String token) throws JsonProcessingException {
        // Логика извлечения информации о пользователе из токена
        String[] chunks = token.split("\\.");
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        HashMap<String, String> params = new ObjectMapper().readValue(payload, HashMap.class);
        return User.withUsername(params.get("name"))
                .password(params.get("password"))
                .roles(params.get("scope"))
                .build();
    }
}


