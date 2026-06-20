package com.project.fitness_backend.security;

import com.project.fitness_backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;
    public String generateToken(User user ){
         return Jwts.builder()
                 .setSubject(String.valueOf(user.getUserId()))
                 .claim("role",user.getRole().name())
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis()+expiration))
                 .signWith(getSignKey())
                 .compact();
    }
    public String extractUserId(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public String extractRole(String token){
        return extractClaim(token,claims->claims.get("role",String.class));
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        try{
            String userId=extractUserId(token);
            return userId!=null && !isValidToken(token);
        }catch(Exception e){
            return false;
        }
    }
    private boolean isValidToken(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
    private <T> T extractClaim(String token, Function<Claims,T>claimsResolver){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }
    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
