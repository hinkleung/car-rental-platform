package com.prudential.assignment.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import static com.prudential.assignment.common.constants.Constants.REQUEST_CONTENT_USERNAME;
import static com.prudential.assignment.common.constants.Constants.REQUEST_CONTENT_USER_ID;

public class JWTUtils {

    private static final String issuer = "auth0";

    private static final Algorithm algorithm = Algorithm.HMAC256("t621bt36821b38612738128");

    public static String generate(String username, Long userId) {
        String token = "";
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim(REQUEST_CONTENT_USERNAME, username)
                    .withClaim(REQUEST_CONTENT_USER_ID, userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + (long) 3600 * 2000))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * verify the jwt, include time verify
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * decode the token
     *
     * @param token
     * @return
     */
    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

}
