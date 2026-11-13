package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaDTOTest {

    @Test
    @DisplayName("Construtor com Matricula completa deve mapear todos os dados incluindo Ciclo")
    void testConstrutorComMatriculaCompleta() {
        Aluno aluno = new Aluno(1L);
        aluno.setNome("Carlos Silva");

        Ciclo ciclo = new Ciclo();
        ciclo.setId(10L);
        ciclo.setNome("Ciclo 2026.1");
        ciclo.setDataInicio(LocalDate.of(2026, 1, 1));
        ciclo.setDataFim(LocalDate.of(2026, 6, 30));

        ProgramaAula programa = new ProgramaAula();
        programa.setId(20L);
        programa.setNome("Forró Intermediário");
        programa.setCiclo(ciclo);

        Matricula matricula = new Matricula();
        matricula.setId(100L);
        matricula.setData(LocalDate.of(2026, 2, 1));
        matricula.setFlAtivo(true);
        matricula.setAluno(aluno);
        matricula.setProgramaAula(programa);
        matricula.setValor(new BigDecimal("300.00"));
        matricula.setValorMensalidade(new BigDecimal("150.00"));
        matricula.setDiaVencimento(5);

        MatriculaDTO dto = new MatriculaDTO(matricula);

        assertEquals(100L, dto.getId());
        assertEquals(LocalDate.of(2026, 2, 1), dto.getData());
        assertTrue(dto.getFlAtivo());
        assertEquals(1L, dto.getAlunoId());
        assertEquals("Carlos Silva", dto.getAlunoNome());
        assertEquals(20L, dto.getProgramaAulaId());
        assertEquals("Forró Intermediário", dto.getProgramaAulaNome());
        assertEquals(LocalDate.of(2026, 1, 1), dto.getCicloDataInicio());
        assertEquals(LocalDate.of(2026, 6, 30), dto.getCicloDataFim());
        assertEquals(new BigDecimal("300.00"), dto.getValor());
        assertEquals(new BigDecimal("150.00"), dto.getValorMensalidade());
        assertEquals(5, dto.getDiaVencimento());
    }

    @Test
    @DisplayName("Construtor com Matricula nula deve retornar com segurança sem lançar exceção")
    void testConstrutorComMatriculaNula() {
        MatriculaDTO dto = new MatriculaDTO((Matricula) null);
        assertNull(dto.getId());
        assertNull(dto.getAlunoId());
        assertNull(dto.getProgramaAulaId());
    }

    @Test
    @DisplayName("Construtor com relacionamentos nulos deve lidar com segurança sem NPE")
    void testConstrutorComRelacionamentosNulos() {
        Matricula matricula = new Matricula();
        matricula.setId(50L);
        matricula.setAluno(null);
        matricula.setProgramaAula(null);

        MatriculaDTO dto = new MatriculaDTO(matricula);

        assertEquals(50L, dto.getId());
        assertNull(dto.getAlunoId());
        assertNull(dto.getAlunoNome());
        assertNull(dto.getProgramaAulaId());
        assertNull(dto.getProgramaAulaNome());
        assertNull(dto.getCicloDataInicio());
    }

    @Test
    @DisplayName("Construtor com ProgramaAula sem Ciclo deve lidar com segurança sem NPE")
    void testConstrutorComProgramaAulaSemCiclo() {
        ProgramaAula programa = new ProgramaAula();
        programa.setId(20L);
        programa.setNome("Samba");
        programa.setCiclo(null);

        Matricula matricula = new Matricula();
        matricula.setProgramaAula(programa);

        MatriculaDTO dto = new MatriculaDTO(matricula);

        assertEquals(20L, dto.getProgramaAulaId());
        assertEquals("Samba", dto.getProgramaAulaNome());
        assertNull(dto.getCicloDataInicio());
        assertNull(dto.getCicloDataFim());
    }

    @Test
    @DisplayName("generateMatricula deve gerar entidade Matricula com valores preenchidos")
    void testGenerateMatricula() {
        LocalDate data = LocalDate.of(2026, 3, 15);
        MatriculaDTO dto = new MatriculaDTO(1L, data, true, 3L, "Aluno Teste", 7L, "Programa Teste", true, 99L);
        dto.setValor(new BigDecimal("250.00"));
        dto.setValorMensalidade(new BigDecimal("100.00"));
        dto.setDiaVencimento(10);

        Matricula matricula = dto.generateMatricula();

        assertNotNull(matricula);
        assertEquals(3L, matricula.getAluno().getId());
        assertEquals(7L, matricula.getProgramaAula().getId());
        assertEquals(LocalDate.now(), matricula.getData());
        assertTrue(matricula.getFlAtivo());
        assertEquals(new BigDecimal("250.00"), matricula.getValor());
        assertEquals(new BigDecimal("100.00"), matricula.getValorMensalidade());
        assertEquals(10, matricula.getDiaVencimento());
    }

    @Test
    @DisplayName("Construtores sobrecarregados e getters/setters")
    void testConstrutoresSobrecarregadosEGettersSetters() {
        LocalDate data = LocalDate.of(2026, 3, 15);
        MatriculaDTO dto1 = new MatriculaDTO(1L, data, true, 2L, "Ana", 3L, "Balé", true);
        assertEquals(1L, dto1.getId());
        assertEquals(data, dto1.getData());
        assertTrue(dto1.getFlAtivo());
        assertEquals(2L, dto1.getAlunoId());
        assertEquals("Ana", dto1.getAlunoNome());
        assertEquals(3L, dto1.getProgramaAulaId());
        assertEquals("Balé", dto1.getProgramaAulaNome());
        assertTrue(dto1.getPresente());
        assertNull(dto1.getPresencaId());

        MatriculaDTO dto2 = new MatriculaDTO(10L, data, false, 20L, "Bia", 30L, "Jazz", false, 99L);
        assertEquals(10L, dto2.getId());
        assertEquals(data, dto2.getData());
        assertEquals(20L, dto2.getAlunoId());
        assertEquals("Bia", dto2.getAlunoNome());
        assertEquals(30L, dto2.getProgramaAulaId());
        assertEquals("Jazz", dto2.getProgramaAulaNome());
        assertFalse(dto2.getPresente());
        assertEquals(99L, dto2.getPresencaId());

        dto2.setPresente(true);
        dto2.setPresencaId(100L);
        dto2.setFlAtivo(true);
        dto2.setValor(new BigDecimal("350.00"));
        dto2.setValorMensalidade(new BigDecimal("120.00"));
        dto2.setDiaVencimento(20);
        dto2.setCicloDataInicio(LocalDate.of(2026, 1, 10));
        dto2.setCicloDataFim(LocalDate.of(2026, 7, 10));

        assertTrue(dto2.getPresente());
        assertEquals(100L, dto2.getPresencaId());
        assertTrue(dto2.getFlAtivo());
        assertEquals(new BigDecimal("350.00"), dto2.getValor());
        assertEquals(new BigDecimal("120.00"), dto2.getValorMensalidade());
        assertEquals(20, dto2.getDiaVencimento());
        assertEquals(LocalDate.of(2026, 1, 10), dto2.getCicloDataInicio());
        assertEquals(LocalDate.of(2026, 7, 10), dto2.getCicloDataFim());
    }
}
