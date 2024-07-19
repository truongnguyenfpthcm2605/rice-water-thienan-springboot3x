package org.website.thienan.ricewaterthienan.security.jwtoauth2;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.security.userprincal.AccountService;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTOAuth2ServerProvider {

    public final String SECRET_KEY = "AigK8h26tzmIrT5E8P5LFlIf8LYeKAOuspPH0B2WyDgJMww3+5kqNF1ieI/WY8fZ";

    public String generateToken(AccountService accountService) throws Exception {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountService.getEmail())
                .issuer("truong-coder")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", accountService.getAuthorities())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
        return jwsObject.serialize();
    }

    // Refresh Token ,if accessToken expired , Client send refresh Token and get new accessToken with new time
    public String generateRefreshToken(String token) throws Exception {
        if (verifyToken(token)) {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Object scope = signedJWT.getJWTClaimsSet().getClaim("scope");
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(signedJWT.getJWTClaimsSet().getSubject())
                    .issuer(signedJWT.getJWTClaimsSet().getIssuer())
                    .issueTime(new Date())
                    .expirationTime(
                            new Date(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()))
                    .jwtID(signedJWT.getJWTClaimsSet().getJWTID())
                    .claim("scope", scope)
                    .build();
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } else {
            throw new Exception("Invalid token");
        }
    }

    public Boolean verifyToken(String token) throws Exception {
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        return signedJWT.verify(verifier) && expirationDate.after(new Date());
    }

    public String getSubjectFromToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }
}
