package ru.antonovmikhail.server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;

public class Controller {
    @GetMapping("/oidc-principal")
    public OidcUser getOidcUserPrincipal(
            @AuthenticationPrincipal OidcUser principal) {
        return principal;
    }
}
/*
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication.getPrincipal() instanceof OidcUser) {
OidcUser principal = ((OidcUser) authentication.getPrincipal());*/
