package com.demo.ecommerce.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: JwtUtils
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@Slf4j
@UtilityClass
public class JwtUtils {

    private final String SECRET_KEY;
    private final JWSAlgorithm algorithm = JWSAlgorithm.HS256;
    private final byte[] secret;

    static {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        secret = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(String username) {
        JWSSigner signer;
        try {
            signer = new MACSigner(secret);
        } catch (KeyLengthException e) {
            throw new JwtValidationExcpetion("Failed to sign JWT", e);
        }
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600_000)).build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(algorithm), claimsSet);
        try {
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new JwtValidationExcpetion("Failed to sign JWT", e);
        }
    }

    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret);
            if (signedJWT.verify(verifier)) {
                return signedJWT.getJWTClaimsSet().getSubject();
            }
        } catch (Exception e) {
            throw new JwtValidationExcpetion("Failed to sign JWT", e);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            Date exp = jwt.getJWTClaimsSet().getExpirationTime();
            return jwt.verify(new MACVerifier(secret)) && exp.after(new Date());
        } catch (Exception e) {
            throw new JwtValidationExcpetion("Invalid token: " + token, e);
        }
    }

}
