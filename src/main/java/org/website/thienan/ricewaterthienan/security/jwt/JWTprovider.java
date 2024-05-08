package org.website.thienan.ricewaterthienan.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.website.thienan.ricewaterthienan.dto.request.AccountRequest;
import org.website.thienan.ricewaterthienan.dto.response.MessageResponse;
import org.website.thienan.ricewaterthienan.exceptions.ResourceExistingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
public class JWTprovider {

    @Value("${spring.jwt.signature.key}")
    private String sgiNatureKey;


    public String generateToken(AccountRequest account){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getEmail())
                .issuer("gaovanuocthienan.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(account))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign( new MACSigner(sgiNatureKey.getBytes()));
            return jwsObject.serialize();
        }catch (Exception e){
            throw  new ResourceExistingException("Jwt Generation Fail");
        }

    }

    private String buildScope(AccountRequest account) {
        StringJoiner stringJoiner = new StringJoiner("");
        if(!CollectionUtils.isEmpty(account.getRoleDetail())){
            account.getRoleDetail().forEach(s -> {
                stringJoiner.add(s);
            });
        }
        return  stringJoiner.toString();
    }

    public MessageResponse checkValidToken(String token) throws Exception{
        JWSVerifier verifier = new MACVerifier(sgiNatureKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        Boolean check =   signedJWT.verify(verifier) && expirationDate.before(new Date());
        return MessageResponse.builder()
                .message("Check Token")
                .code(100)
                .timeStamp(LocalDateTime.now())
                .data(check)
                .build();
    }
}
