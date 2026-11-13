package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.br.gerenciadoraulas.model.TipoAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramaAulaDTOTest {

    @Test
    @DisplayName("Construtor com ProgramaAula completo mapeia ciclo e tipoAula")
    void testConstrutorCompleto() {
        Ciclo ciclo = new Ciclo();
        ciclo.setId(10L);
        ciclo.setNome("Ciclo 2026");

        TipoAula tipo = new TipoAula();
        tipo.setId(20L);
        tipo.setNome("Dança de Salão");

        ProgramaAula p = new ProgramaAula();
        p.setId(1L);
        p.setNome("Turma Terça 19h");
        p.setCiclo(ciclo);
        p.setTipoAula(tipo);

        ProgramaAulaDTO dto = new ProgramaAulaDTO(p);

        assertEquals(1L, dto.getId());
        assertEquals("Turma Terça 19h", dto.getNome());
        assertEquals(10L, dto.getCicloId());
        assertEquals("Ciclo 2026", dto.getCicloNome());
        assertEquals(20L, dto.getTipoAulaId());
        assertEquals("Dança de Salão", dto.getTipoAulaNome());
    }

    @Test
    @DisplayName("Construtor com ProgramaAula nulo não lança exceção")
    void testConstrutorNulo() {
        ProgramaAulaDTO dto = new ProgramaAulaDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getNome());
        assertNull(dto.getCicloId());
        assertNull(dto.getTipoAulaId());
    }

    @Test
    @DisplayName("Construtor com relacionamentos nulos")
    void testConstrutorRelacionamentosNulos() {
        ProgramaAula p = new ProgramaAula();
        p.setId(2L);
        p.setNome("Turma Especial");
        p.setCiclo(null);
        p.setTipoAula(null);

        ProgramaAulaDTO dto = new ProgramaAulaDTO(p);

        assertEquals(2L, dto.getId());
        assertEquals("Turma Especial", dto.getNome());
        assertNull(dto.getCicloId());
        assertNull(dto.getCicloNome());
        assertNull(dto.getTipoAulaId());
        assertNull(dto.getTipoAulaNome());
    }
}
