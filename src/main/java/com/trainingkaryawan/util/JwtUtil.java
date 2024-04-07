package com.trainingkaryawan.util;

import com.trainingkaryawan.constant.GeneralConstant;
import com.trainingkaryawan.model.response.ClaimUserDetailsModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    //    you can generate key online from this link https://asecuritysite.com/encryption/plain
    private final String secret_key = "ed9a2229559236cc926d018d830d326096515ede415e3cf3aa7e5e9e16565ee7";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, ClaimUserDetailsModel userDetailsModel) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, userDetailsModel);
    }

    private String createToken(Map<String, Object> claims, String subject, ClaimUserDetailsModel userDetailsModel) {

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .claim(GeneralConstant.JWT_CLAIM_USER_DETAILS, userDetailsModel)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //10 hours
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
