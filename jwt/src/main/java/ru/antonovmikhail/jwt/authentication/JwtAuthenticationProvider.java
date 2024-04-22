package ru.antonovmikhail.jwt.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Логика аутентификации с использованием токена
        String userName = authentication.getName();
        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
        String token = authentication.getCredentials().toString();
        if (user == null) throw new BadCredentialsException("Unknown user " + userName);
        if (JwtUtil.validateToken(token, user)) {
            authentication.setAuthenticated(true);
            return authentication;
        }
        throw new BadCredentialsException("Unknown user " + userName);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

