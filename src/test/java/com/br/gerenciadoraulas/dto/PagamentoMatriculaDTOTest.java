package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.PagamentoMatricula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoMatriculaDTOTest {

    @Test
    @DisplayName("Construtor com PagamentoMatricula completo mapeia todos os campos")
    void testConstrutorCompleto() {
        ProgramaAula programa = new ProgramaAula();
        programa.setNome("Tango");

        Matricula matricula = new Matricula();
        matricula.setId(15L);
        matricula.setProgramaAula(programa);

        PagamentoMatricula pm = new PagamentoMatricula();
        pm.setId(100L);
        pm.setValorMensalidadeDia(new BigDecimal("75.50"));
        pm.setMatricula(matricula);

        PagamentoMatriculaDTO dto = new PagamentoMatriculaDTO(pm);

        assertEquals(100L, dto.getId());
        assertEquals(new BigDecimal("75.50"), dto.getValorMensalidadeDia());
        assertEquals(15L, dto.getMatriculaId());
        assertEquals("Tango", dto.getProgramaAulaNome());
    }

    @Test
    @DisplayName("Construtor com PagamentoMatricula nulo não lança exceção")
    void testConstrutorNulo() {
        PagamentoMatriculaDTO dto = new PagamentoMatriculaDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getValorMensalidadeDia());
        assertNull(dto.getMatriculaId());
        assertNull(dto.getProgramaAulaNome());
    }

    @Test
    @DisplayName("Construtor com matricula nula ou programaAula nulo")
    void testConstrutorRelacionamentosNulos() {
        PagamentoMatricula pm1 = new PagamentoMatricula();
        pm1.setId(1L);
        pm1.setMatricula(null);

        PagamentoMatriculaDTO dto1 = new PagamentoMatriculaDTO(pm1);
        assertEquals(1L, dto1.getId());
        assertNull(dto1.getMatriculaId());
        assertNull(dto1.getProgramaAulaNome());

        Matricula matSemPrograma = new Matricula();
        matSemPrograma.setId(2L);
        matSemPrograma.setProgramaAula(null);
        PagamentoMatricula pm2 = new PagamentoMatricula();
        pm2.setMatricula(matSemPrograma);

        PagamentoMatriculaDTO dto2 = new PagamentoMatriculaDTO(pm2);
        assertEquals(2L, dto2.getMatriculaId());
        assertNull(dto2.getProgramaAulaNome());
    }

    @Test
    @DisplayName("Getters e Setters de PagamentoMatriculaDTO")
    void testGettersSetters() {
        PagamentoMatriculaDTO dto = new PagamentoMatriculaDTO();
        dto.setId(10L);
        dto.setValorMensalidadeDia(new BigDecimal("90.00"));
        dto.setMatriculaId(20L);
        dto.setProgramaAulaNome("Zouk");

        assertEquals(10L, dto.getId());
        assertEquals(new BigDecimal("90.00"), dto.getValorMensalidadeDia());
        assertEquals(20L, dto.getMatriculaId());
        assertEquals("Zouk", dto.getProgramaAulaNome());
    }
}
