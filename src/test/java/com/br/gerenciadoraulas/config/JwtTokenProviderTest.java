package com.br.gerenciadoraulas.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenProviderTest {

    @Test
    void generateTokenRetornaTokenNaoVazio() {
        String token = JwtTokenProvider.generateToken("user", true);
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void verifyTokenRetornaSubjectEClaimAdmin() {
        String token = JwtTokenProvider.generateToken("carlos", true);
        DecodedJWT decoded = JwtTokenProvider.verifyToken(token);
        assertEquals("carlos", decoded.getSubject());
        assertTrue(decoded.getClaim("admin").asBoolean());
        assertEquals("gerenciadordeaulas", decoded.getIssuer());
    }

    @Test
    void verifyTokenAdminFalse() {
        String token = JwtTokenProvider.generateToken("anon", false);
        DecodedJWT decoded = JwtTokenProvider.verifyToken(token);
        assertFalse(decoded.getClaim("admin").asBoolean());
    }

    @Test
    void verifyTokenInvalidoLancaExcecao() {
        assertThrows(Exception.class, () -> JwtTokenProvider.verifyToken("token.invalido.aqui"));
    }

    @Test
    void verifyTokenAssinaturaAlteradaLancaExcecao() {
        String token = JwtTokenProvider.generateToken("user", true);
        String adulterado = token.substring(0, token.length() - 3) + "abc";
        assertThrows(Exception.class, () -> JwtTokenProvider.verifyToken(adulterado));
    }
}
