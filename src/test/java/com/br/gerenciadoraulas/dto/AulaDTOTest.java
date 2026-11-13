package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AulaDTOTest {

    @Test
    @DisplayName("Construtor com Aula completa mapeia id, nome, data e programaAula")
    void testConstrutorCompleto() {
        ProgramaAula programa = new ProgramaAula();
        programa.setId(5L);
        programa.setNome("Forró Avançado");

        LocalDateTime data = LocalDateTime.of(2026, 4, 10, 19, 30);

        Aula aula = new Aula();
        aula.setId(1L);
        aula.setNome("Aula 1 - Giros");
        aula.setData(data);
        aula.setProgramaAula(programa);

        AulaDTO dto = new AulaDTO(aula);

        assertEquals(1L, dto.getId());
        assertEquals("Aula 1 - Giros", dto.getNome());
        assertEquals(data, dto.getData());
        assertEquals(5L, dto.getProgramaAulaId());
        assertEquals("Forró Avançado", dto.getProgramaAulaNome());
    }

    @Test
    @DisplayName("Construtor com Aula nula não lança exceção")
    void testConstrutorNulo() {
        AulaDTO dto = new AulaDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getNome());
        assertNull(dto.getData());
        assertNull(dto.getProgramaAulaId());
    }

    @Test
    @DisplayName("Construtor com Aula sem ProgramaAula")
    void testConstrutorSemProgramaAula() {
        Aula aula = new Aula();
        aula.setId(2L);
        aula.setNome("Aula Livre");
        aula.setProgramaAula(null);

        AulaDTO dto = new AulaDTO(aula);

        assertEquals(2L, dto.getId());
        assertEquals("Aula Livre", dto.getNome());
        assertNull(dto.getProgramaAulaId());
        assertNull(dto.getProgramaAulaNome());
    }
}
