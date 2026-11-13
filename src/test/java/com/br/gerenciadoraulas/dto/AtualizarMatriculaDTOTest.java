package com.br.gerenciadoraulas.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarMatriculaDTOTest {

    @Test
    @DisplayName("Construtores e getters/setters de AtualizarMatriculaDTO")
    void testConstrutoresEGettersSetters() {
        AtualizarMatriculaDTO dto1 = new AtualizarMatriculaDTO(new BigDecimal("100.00"), new BigDecimal("90.00"), 5);
        assertEquals(new BigDecimal("100.00"), dto1.getValor());
        assertEquals(new BigDecimal("90.00"), dto1.getValorMensalidade());
        assertEquals(5, dto1.getDiaVencimento());
        assertNull(dto1.getFlAtivo());

        AtualizarMatriculaDTO dto2 = new AtualizarMatriculaDTO(new BigDecimal("200.00"), new BigDecimal("180.00"), 10, true);
        assertEquals(new BigDecimal("200.00"), dto2.getValor());
        assertEquals(new BigDecimal("180.00"), dto2.getValorMensalidade());
        assertEquals(10, dto2.getDiaVencimento());
        assertTrue(dto2.getFlAtivo());

        AtualizarMatriculaDTO dto3 = new AtualizarMatriculaDTO();
        dto3.setValor(new BigDecimal("300.00"));
        dto3.setValorMensalidade(new BigDecimal("250.00"));
        dto3.setDiaVencimento(15);
        dto3.setFlAtivo(false);

        assertEquals(new BigDecimal("300.00"), dto3.getValor());
        assertEquals(new BigDecimal("250.00"), dto3.getValorMensalidade());
        assertEquals(15, dto3.getDiaVencimento());
        assertFalse(dto3.getFlAtivo());
    }
}
