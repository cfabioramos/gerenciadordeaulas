package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoDTOTest {

    @Test
    @DisplayName("Construtor com Aluno mapeia id e nome")
    void testConstrutorComAluno() {
        Aluno aluno = new Aluno(1L);
        aluno.setNome("Ana Paula");

        AlunoDTO dto = new AlunoDTO(aluno);

        assertEquals(1L, dto.getId());
        assertEquals("Ana Paula", dto.getNome());
        assertNull(dto.getPresente());
    }

    @Test
    @DisplayName("Construtor com Aluno nulo não lança exceção")
    void testConstrutorComAlunoNulo() {
        AlunoDTO dto = new AlunoDTO((Aluno) null);
        assertNull(dto.getId());
        assertNull(dto.getNome());
    }

    @Test
    @DisplayName("Construtor com 3 parâmetros e setters")
    void testConstrutorCompletoESetters() {
        AlunoDTO dto = new AlunoDTO(2L, "Lucas", true);

        assertEquals(2L, dto.getId());
        assertEquals("Lucas", dto.getNome());
        assertTrue(dto.getPresente());

        dto.setId(3L);
        dto.setNome("Lucas Silva");
        dto.setPresente(false);

        assertEquals(3L, dto.getId());
        assertEquals("Lucas Silva", dto.getNome());
        assertFalse(dto.getPresente());
    }
}
