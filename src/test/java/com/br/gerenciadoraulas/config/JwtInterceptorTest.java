package com.br.gerenciadoraulas.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtInterceptorTest {

    private JwtInterceptor interceptor;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        interceptor = new JwtInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void optionsEhPermitidoSemToken() throws Exception {
        request.setMethod("OPTIONS");
        assertTrue(interceptor.preHandle(request, response, new Object()));
        assertEquals(200, response.getStatus());
    }

    @Test
    void semHeaderRetorna401() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/alunos");
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(401, response.getStatus());
    }

    @Test
    void tokenInvalidoRetorna401() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/alunos");
        request.addHeader("Authorization", "Bearer token.invalido");
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(401, response.getStatus());
    }

    @Test
    void tokenAdminPermiteEscrita() throws Exception {
        String token = JwtTokenProvider.generateToken("admin", true);
        request.setMethod("POST");
        request.setRequestURI("/alunos");
        request.addHeader("Authorization", "Bearer " + token);
        assertTrue(interceptor.preHandle(request, response, new Object()));
        assertEquals(true, request.getAttribute("isAdmin"));
        assertEquals("admin", request.getAttribute("username"));
    }

    @Test
    void tokenNaoAdminBloqueiaEscrita() throws Exception {
        String token = JwtTokenProvider.generateToken("user", false);
        request.setMethod("POST");
        request.setRequestURI("/alunos");
        request.addHeader("Authorization", "Bearer " + token);
        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals(403, response.getStatus());
    }

    @Test
    void tokenNaoAdminPermiteLeitura() throws Exception {
        String token = JwtTokenProvider.generateToken("user", false);
        request.setMethod("GET");
        request.setRequestURI("/alunos");
        request.addHeader("Authorization", "Bearer " + token);
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }

    @Test
    void atualizacaoDeTemaPermitidaParaNaoAdmin() throws Exception {
        String token = JwtTokenProvider.generateToken("user", false);
        request.setMethod("PUT");
        request.setRequestURI("/auth/theme");
        request.addHeader("Authorization", "Bearer " + token);
        assertTrue(interceptor.preHandle(request, response, new Object()));
    }
}
