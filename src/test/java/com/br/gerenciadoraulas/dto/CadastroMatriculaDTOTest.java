package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Matricula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CadastroMatriculaDTOTest {

    @Test
    @DisplayName("generateMatricula deve criar Matricula com data informada e campos mapeados")
    void testGenerateMatriculaComDataInformada() {
        CadastroMatriculaDTO dto = new CadastroMatriculaDTO(10L, 20L);
        LocalDate data = LocalDate.of(2026, 5, 10);
        dto.setData(data);
        dto.setValor(new BigDecimal("150.00"));
        dto.setValorMensalidade(new BigDecimal("120.00"));
        dto.setDiaVencimento(15);

        Matricula matricula = dto.generateMatricula();

        assertNotNull(matricula);
        assertNotNull(matricula.getAluno());
        assertEquals(10L, matricula.getAluno().getId());
        assertNotNull(matricula.getProgramaAula());
        assertEquals(20L, matricula.getProgramaAula().getId());
        assertEquals(data, matricula.getData());
        assertTrue(matricula.getFlAtivo());
        assertEquals(new BigDecimal("150.00"), matricula.getValor());
        assertEquals(new BigDecimal("120.00"), matricula.getValorMensalidade());
        assertEquals(15, matricula.getDiaVencimento());
    }

    @Test
    @DisplayName("generateMatricula deve usar LocalDate.now() quando data for nula")
    void testGenerateMatriculaComDataNula() {
        CadastroMatriculaDTO dto = new CadastroMatriculaDTO();
        dto.setAlunoId(5L);
        dto.setProgramaAulaId(8L);
        dto.setData(null);

        LocalDate antes = LocalDate.now().minusDays(1);
        Matricula matricula = dto.generateMatricula();
        LocalDate depois = LocalDate.now().plusDays(1);

        assertNotNull(matricula);
        assertNotNull(matricula.getData());
        assertTrue(matricula.getData().isAfter(antes) && matricula.getData().isBefore(depois));
        assertTrue(matricula.getFlAtivo());
    }

    @Test
    @DisplayName("Deve cobrir getters e setters de CadastroMatriculaDTO")
    void testGettersSetters() {
        CadastroMatriculaDTO dto = new CadastroMatriculaDTO();
        dto.setAlunoId(1L);
        dto.setProgramaAulaId(2L);
        dto.setValor(new BigDecimal("200.00"));
        dto.setValorMensalidade(new BigDecimal("180.00"));
        dto.setDiaVencimento(10);
        LocalDate data = LocalDate.of(2026, 6, 1);
        dto.setData(data);

        assertEquals(1L, dto.getAlunoId());
        assertEquals(2L, dto.getProgramaAulaId());
        assertEquals(new BigDecimal("200.00"), dto.getValor());
        assertEquals(new BigDecimal("180.00"), dto.getValorMensalidade());
        assertEquals(10, dto.getDiaVencimento());
        assertEquals(data, dto.getData());
    }
}
