package ru.antonovmikhail.jwt.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static String generateToken(UserDetails userDetails) throws UnsupportedEncodingException {
        // Логика генерации JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(userDetails.getPassword());
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .issuer("JwtApp")
                .subject(userDetails.getUsername())
                .claim("name", userDetails.getUsername())
                .claim("password", userDetails.getPassword())
                .claim("scope", userDetails.getAuthorities())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.ofEpochSecond(Instant.now().getEpochSecond() + 72000)))
                .signWith(signingKey)
                .compact();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parser()
                    .requireIssuer("JwtApp")
                    .verifyWith(generateKey(userDetails.getPassword()))
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static SecretKey generateKey(String password) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(password);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

}
