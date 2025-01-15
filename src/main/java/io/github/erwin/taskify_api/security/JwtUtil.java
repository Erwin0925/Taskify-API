package io.github.erwin.taskify_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY;

    public JwtUtil() {
        // Use a fixed secret key (base64-encoded for security)
        String secret = "KjVnX7Ap1eNyz/P2LjF9U4XQzcGRtmcqXh7Irz/OwbI=";
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        SECRET_KEY = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, String username){
        String tokenUsername = extractUsername(token);
        return(tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
