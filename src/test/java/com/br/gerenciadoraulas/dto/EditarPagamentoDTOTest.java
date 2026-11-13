package com.br.gerenciadoraulas.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EditarPagamentoDTOTest {

    @Test
    @DisplayName("Getters e Setters de EditarPagamentoDTO")
    void testGettersSetters() {
        EditarPagamentoDTO dto = new EditarPagamentoDTO();
        dto.setValor(new BigDecimal("99.90"));
        LocalDate data = LocalDate.of(2026, 9, 5);
        dto.setData(data);

        assertEquals(new BigDecimal("99.90"), dto.getValor());
        assertEquals(data, dto.getData());
    }
}
