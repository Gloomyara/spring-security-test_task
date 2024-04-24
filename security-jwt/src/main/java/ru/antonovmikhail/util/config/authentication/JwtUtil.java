package ru.antonovmikhail.util.config.authentication;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;


public class JwtUtil {
    @Value("${spring.application.name}")
    private static String appName;

    public static String generateToken(UserDetails userDetails) {
        // Логика генерации JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(userDetails.getPassword());
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, Jwts.SIG.RS256.toString());
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        return Jwts.builder()
                .issuer("JwtApp")
                .subject(userDetails.getUsername())
                .claim("name", userDetails.getUsername())
                .claim("password", userDetails.getPassword())
                .claim("scope", scope)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 72000)))
                .signWith(signingKey)
                .compact();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parser()
                    .requireIssuer(appName)
                    .verifyWith(generateKey(userDetails.getPassword()))
                    .build()
                    .parse(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static SecretKey generateKey(String password) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(password);
        return new SecretKeySpec(apiKeySecretBytes, Jwts.SIG.RS256.toString());
    }

}
