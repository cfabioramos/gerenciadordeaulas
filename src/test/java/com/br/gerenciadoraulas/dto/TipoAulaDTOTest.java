package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.TipoAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoAulaDTOTest {

    @Test
    @DisplayName("Construtor com TipoAula mapeia id e nome")
    void testConstrutorComTipoAula() {
        TipoAula tipo = new TipoAula();
        tipo.setId(5L);
        tipo.setNome("Dança Contemporânea");

        TipoAulaDTO dto = new TipoAulaDTO(tipo);

        assertEquals(5L, dto.getId());
        assertEquals("Dança Contemporânea", dto.getNome());
    }

    @Test
    @DisplayName("Construtor com TipoAula nulo não lança exceção")
    void testConstrutorComTipoAulaNulo() {
        TipoAulaDTO dto = new TipoAulaDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getNome());
    }
}
