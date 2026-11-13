package com.br.gerenciadoraulas.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CadastroPagamentoDTOTest {

    @Test
    @DisplayName("Getters e Setters de CadastroPagamentoDTO")
    void testGettersSetters() {
        CadastroPagamentoDTO dto = new CadastroPagamentoDTO();
        dto.setValor(new BigDecimal("150.00"));
        LocalDate data = LocalDate.of(2026, 8, 20);
        dto.setData(data);
        dto.setMatriculaIds(List.of(1L, 2L, 3L));

        assertEquals(new BigDecimal("150.00"), dto.getValor());
        assertEquals(data, dto.getData());
        assertEquals(List.of(1L, 2L, 3L), dto.getMatriculaIds());
    }
}
