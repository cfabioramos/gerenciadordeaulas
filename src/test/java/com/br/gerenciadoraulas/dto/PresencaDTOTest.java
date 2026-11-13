package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Presenca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PresencaDTOTest {

    @Test
    @DisplayName("Construtor com Presenca completa mapeia id, aulaId e matriculaId")
    void testConstrutorCompleto() {
        Aula aula = new Aula();
        aula.setId(10L);

        Matricula matricula = new Matricula();
        matricula.setId(20L);

        Presenca presenca = new Presenca();
        presenca.setId(100L);
        presenca.setAula(aula);
        presenca.setMatricula(matricula);

        PresencaDTO dto = new PresencaDTO(presenca);

        assertEquals(100L, dto.getId());
        assertEquals(10L, dto.getAulaId());
        assertEquals(20L, dto.getMatriculaId());
    }

    @Test
    @DisplayName("Construtor com Presenca nula não lança exceção")
    void testConstrutorNulo() {
        PresencaDTO dto = new PresencaDTO((Presenca) null);
        assertNull(dto.getId());
        assertNull(dto.getAulaId());
        assertNull(dto.getMatriculaId());
    }

    @Test
    @DisplayName("Construtor com relacionamentos nulos")
    void testConstrutorRelacionamentosNulos() {
        Presenca presenca = new Presenca();
        presenca.setId(5L);
        presenca.setAula(null);
        presenca.setMatricula(null);

        PresencaDTO dto = new PresencaDTO(presenca);

        assertEquals(5L, dto.getId());
        assertNull(dto.getAulaId());
        assertNull(dto.getMatriculaId());
    }

    @Test
    @DisplayName("generatePresenca deve gerar entidade com id, aula e matricula")
    void testGeneratePresencaCompleto() {
        PresencaDTO dto = new PresencaDTO(30L, 40L);
        dto.setId(1L);

        Presenca presenca = dto.generatePresenca();

        assertNotNull(presenca);
        assertEquals(1L, presenca.getId());
        assertNotNull(presenca.getMatricula());
        assertEquals(30L, presenca.getMatricula().getId());
        assertNotNull(presenca.getAula());
        assertEquals(40L, presenca.getAula().getId());
    }

    @Test
    @DisplayName("generatePresenca com campos nulos deve gerar entidade vazia")
    void testGeneratePresencaCamposNulos() {
        PresencaDTO dto = new PresencaDTO();

        Presenca presenca = dto.generatePresenca();

        assertNotNull(presenca);
        assertNull(presenca.getId());
        assertNull(presenca.getMatricula());
        assertNull(presenca.getAula());
    }

    @Test
    @DisplayName("Getters e Setters de PresencaDTO")
    void testGettersSetters() {
        PresencaDTO dto = new PresencaDTO();
        dto.setId(15L);
        dto.setAulaId(25L);
        dto.setMatriculaId(35L);

        assertEquals(15L, dto.getId());
        assertEquals(25L, dto.getAulaId());
        assertEquals(35L, dto.getMatriculaId());
    }
}
