package com.diary.demo.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    public static final String SECRET = "745C372F54537B888C02D7B5AFE65E69B9B2EC0B1F11B879D38C1711538D7674C83B5C3500EAEAAC94AD6F5EFF626EE2DF047A567DDCC22AAB9DD4326497D8A2";

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", "https://secure.emotiondiary.com");
//        claims.put("name", "Oleg");
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

//    public String extractUsername(String jwt) {
//        Claims claims = getClaims(jwt);
//        return claims.getSubject();
//    }

//    private Claims getClaims(String jwt) {
//        return Jwts.parser()
//                .verifyWith(generateKey())
//                .build()
//                .parseSignedClaims(jwt)
//                .getPayload();
//    }

//    public boolean isTokenValid(String jwt) {
//        Claims claims = getClaims(jwt);
//        return claims.getExpiration().after(Date.from(Instant.now()));
//    }

}
