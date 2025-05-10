package com.example.twitter_clone.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.twitter_clone.util.enums.AuthenticationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${security.jwt.secret}")
    private String secret;

    public String generateToken(UserClaimData claimData){
        Date expirationDate = Date.from(ZonedDateTime.now().plusWeeks(2).toInstant());
        return JWT.create()
                .withSubject("user details")
                .withClaim("claim", claimData.getClaim())
                .withClaim("type", claimData.getType().toString())
                .withIssuedAt(new Date())
                .withIssuer("twitter_clone")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("user details")
                .withIssuer("twitter_clone")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("claim").asString();
    }
}
