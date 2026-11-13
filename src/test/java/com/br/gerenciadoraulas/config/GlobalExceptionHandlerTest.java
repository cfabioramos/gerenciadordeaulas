package com.br.gerenciadoraulas.config;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBusinessRuleRetorna400() {
        IllegalArgumentException ex = new IllegalArgumentException("regra violada");
        ResponseEntity<Map<String, String>> resp = handler.handleBusinessRuleException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("regra violada", resp.getBody().get("message"));
        assertEquals("regra violada", resp.getBody().get("error"));
    }

    @Test
    void handleIllegalStateRetorna400() {
        IllegalStateException ex = new IllegalStateException("estado inválido");
        ResponseEntity<Map<String, String>> resp = handler.handleBusinessRuleException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    void handleDataIntegrityRetorna409() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("fk");
        ResponseEntity<Map<String, String>> resp = handler.handleDataIntegrityViolation(ex);
        assertEquals(HttpStatus.CONFLICT, resp.getStatusCode());
        assertEquals("Não é possível realizar esta operação pois existem registros dependentes vinculados.", resp.getBody().get("message"));
    }
}
