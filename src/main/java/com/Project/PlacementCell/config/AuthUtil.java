package com.Project.PlacementCell.config;

import com.Project.PlacementCell.Entity.Student;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretkey}")
    private String jwtsecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtsecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(int id,String role){
       return Jwts.builder()
               .setSubject(String.valueOf(id))
               .claim("userId",id)
               .claim("role",role)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
               .signWith(getSecretKey())
               .compact();
    }

}
