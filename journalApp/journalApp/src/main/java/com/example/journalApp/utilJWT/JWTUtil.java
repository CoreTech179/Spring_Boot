package com.example.journalApp.utilJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@Component
public class JWTUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> information = new HashMap<>(); // To send any kind of data in jwt then with the help of this "information" Map we can pass through it
        return createToken(information, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims) // actual data coming from information Map
                .subject(username) // identifier (i.e.how to identify a user? Obviously by its username --> therefore in subject username is passed.)
                .header().empty().add("typ","JWT") // jwt header (i.e.Type of Token and Algorithm is used)
                .and()
                .issuedAt(new Date(System.currentTimeMillis())) // set at which time this particular jwt token is generated.
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // let's say after 60 minutes jwt token will expire
                .signWith(getSigningKey()) // combine the Base64Encode(Header + "." + payload) with a particular Algorithm
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }



}
