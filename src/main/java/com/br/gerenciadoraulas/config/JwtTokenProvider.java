package com.br.gerenciadoraulas.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JwtTokenProvider {
    public static final String SECRET = "marcosaffonsodanca_secret_key_2026";
    private static final long EXPIRATION_TIME = 10 * 60 * 1000; // 10 minutes in milliseconds
    private static final String ISSUER = "gerenciadordeaulas";

    public static String generateToken(String username, boolean isAdmin) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(username)
                .withClaim("admin", isAdmin)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(token);
    }
}
