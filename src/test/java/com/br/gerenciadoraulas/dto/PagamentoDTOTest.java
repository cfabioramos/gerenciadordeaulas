package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoDTOTest {

    @Test
    @DisplayName("Construtor com Pagamento completo deve mapear itens, aluno e ciclo")
    void testConstrutorComPagamentoCompleto() {
        Aluno aluno = new Aluno(1L);
        aluno.setNome("Mariana");

        Ciclo ciclo = new Ciclo();
        ciclo.setId(10L);
        ciclo.setNome("Ciclo 2026.2");

        ProgramaAula programa = new ProgramaAula();
        programa.setId(20L);
        programa.setNome("Dança de Salão");
        programa.setCiclo(ciclo);

        Matricula matricula = new Matricula();
        matricula.setId(100L);
        matricula.setAluno(aluno);
        matricula.setProgramaAula(programa);

        PagamentoMatricula pm = new PagamentoMatricula();
        pm.setId(500L);
        pm.setValorMensalidadeDia(new BigDecimal("80.00"));
        pm.setMatricula(matricula);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(50L);
        pagamento.setValor(new BigDecimal("160.00"));
        pagamento.setData(LocalDate.of(2026, 4, 15));
        pagamento.setPagamentoMatriculas(List.of(pm));

        PagamentoDTO dto = new PagamentoDTO(pagamento);

        assertEquals(50L, dto.getId());
        assertEquals(new BigDecimal("160.00"), dto.getValor());
        assertEquals(LocalDate.of(2026, 4, 15), dto.getData());
        assertEquals(1, dto.getItens().size());
        assertEquals(500L, dto.getItens().get(0).getId());
        assertEquals(1L, dto.getAlunoId());
        assertEquals("Mariana", dto.getAlunoNome());
        assertEquals(10L, dto.getCicloId());
        assertEquals("Ciclo 2026.2", dto.getCicloNome());
    }

    @Test
    @DisplayName("Construtor com Pagamento nulo deve lidar com segurança sem lançar exceção")
    void testConstrutorComPagamentoNulo() {
        PagamentoDTO dto = new PagamentoDTO(null);
        assertNull(dto.getId());
        assertNull(dto.getValor());
        assertNull(dto.getData());
        assertTrue(dto.getItens().isEmpty());
    }

    @Test
    @DisplayName("Construtor com Pagamento sem matriculas associadas")
    void testConstrutorComPagamentoSemMatriculas() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(2L);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setPagamentoMatriculas(null);

        PagamentoDTO dto = new PagamentoDTO(pagamento);

        assertEquals(2L, dto.getId());
        assertEquals(new BigDecimal("100.00"), dto.getValor());
        assertTrue(dto.getItens().isEmpty());
        assertNull(dto.getAlunoId());
        assertNull(dto.getCicloId());
    }

    @Test
    @DisplayName("Construtor com PagamentoMatricula contendo relacionamentos nulos")
    void testConstrutorComRelacionamentosNulos() {
        PagamentoMatricula pmSemMatricula = new PagamentoMatricula();
        pmSemMatricula.setId(1L);

        Matricula matriculaVazia = new Matricula();
        matriculaVazia.setId(2L);
        matriculaVazia.setAluno(null);
        matriculaVazia.setProgramaAula(null);

        PagamentoMatricula pmComMatriculaVazia = new PagamentoMatricula();
        pmComMatriculaVazia.setId(2L);
        pmComMatriculaVazia.setMatricula(matriculaVazia);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(3L);
        pagamento.setPagamentoMatriculas(List.of(pmSemMatricula, pmComMatriculaVazia));

        PagamentoDTO dto = new PagamentoDTO(pagamento);

        assertEquals(3L, dto.getId());
        assertEquals(2, dto.getItens().size());
        assertNull(dto.getAlunoId());
        assertNull(dto.getCicloId());
    }

    @Test
    @DisplayName("Getters e Setters de PagamentoDTO")
    void testGettersSetters() {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(10L);
        dto.setValor(new BigDecimal("220.00"));
        LocalDate data = LocalDate.of(2026, 7, 20);
        dto.setData(data);
        dto.setAlunoId(1L);
        dto.setAlunoNome("João");
        dto.setCicloId(2L);
        dto.setCicloNome("Ciclo 1");
        dto.setItens(Collections.emptyList());

        assertEquals(10L, dto.getId());
        assertEquals(new BigDecimal("220.00"), dto.getValor());
        assertEquals(data, dto.getData());
        assertEquals(1L, dto.getAlunoId());
        assertEquals("João", dto.getAlunoNome());
        assertEquals(2L, dto.getCicloId());
        assertEquals("Ciclo 1", dto.getCicloNome());
        assertTrue(dto.getItens().isEmpty());
    }
}
