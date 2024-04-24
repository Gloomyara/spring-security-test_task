package ru.antonovmikhail.user.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.antonovmikhail.util.config.authentication.CustomUserDetails;
import ru.antonovmikhail.util.config.authentication.JwtUtil;

@RestController("api/v1")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService service;

    public AuthController(AuthenticationManager authManager,
    UserDetailsService service) {
        this.authManager = authManager;
        this.service = service;

    }

    private record LoginRequest(String username, String password) {};
    private record LoginResponse(String message, String access_jwt_token) {};
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username, request.password);
        Authentication auth = authManager.authenticate(authenticationToken);

        CustomUserDetails user = (CustomUserDetails) service.loadUserByUsername(request.username);
        String access_token = JwtUtil.generateToken(user);

        return new LoginResponse("User with login = "+ request.username + " successfully logined!"
                , access_token);
    }

}
