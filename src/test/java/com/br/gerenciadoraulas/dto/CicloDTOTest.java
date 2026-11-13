package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CicloDTOTest {

    @Test
    @DisplayName("Construtor com Ciclo completo deve mapear programaAulaIds")
    void testConstrutorCompleto() {
        ProgramaAula p1 = new ProgramaAula();
        p1.setId(10L);
        ProgramaAula p2 = new ProgramaAula();
        p2.setId(20L);

        Ciclo ciclo = new Ciclo();
        ciclo.setId(1L);
        ciclo.setNome("Ciclo 2026.1");
        ciclo.setDataInicio(LocalDate.of(2026, 2, 1));
        ciclo.setDataFim(LocalDate.of(2026, 7, 31));
        ciclo.setProgramaAulas(List.of(p1, p2));

        CicloDTO dto = new CicloDTO(ciclo);

        assertEquals(1L, dto.getId());
        assertEquals("Ciclo 2026.1", dto.getNome());
        assertEquals(LocalDate.of(2026, 2, 1), dto.getDataInicio());
        assertEquals(LocalDate.of(2026, 7, 31), dto.getDataFim());
        assertEquals(List.of(10L, 20L), dto.getProgramaAulaIds());
    }

    @Test
    @DisplayName("Construtor com Ciclo nulo não lança exceção")
    void testConstrutorNulo() {
        CicloDTO dto = new CicloDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getNome());
        assertTrue(dto.getProgramaAulaIds().isEmpty());
    }

    @Test
    @DisplayName("Construtor com Ciclo sem programaAulas deve ter lista vazia")
    void testConstrutorSemProgramaAulas() {
        Ciclo ciclo = new Ciclo();
        ciclo.setId(2L);
        ciclo.setProgramaAulas(null);

        CicloDTO dto = new CicloDTO(ciclo);

        assertEquals(2L, dto.getId());
        assertTrue(dto.getProgramaAulaIds().isEmpty());
    }
}
