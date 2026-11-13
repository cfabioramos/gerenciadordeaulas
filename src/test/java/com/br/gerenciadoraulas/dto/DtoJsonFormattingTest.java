package com.br.gerenciadoraulas.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoJsonFormattingTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("CadastroMatriculaDTO deve desserializar data no formato dd/MM/yyyy")
    void testCadastroMatriculaJsonFormat() throws Exception {
        String json = "{\"alunoId\":1,\"programaAulaId\":2,\"data\":\"25/12/2026\",\"valor\":150.00}";
        CadastroMatriculaDTO dto = objectMapper.readValue(json, CadastroMatriculaDTO.class);

        assertEquals(LocalDate.of(2026, 12, 25), dto.getData());
        assertEquals(1L, dto.getAlunoId());
        assertEquals(2L, dto.getProgramaAulaId());
        assertEquals(new BigDecimal("150.00"), dto.getValor());
    }

    @Test
    @DisplayName("CadastroPagamentoDTO deve desserializar data no formato dd/MM/yyyy")
    void testCadastroPagamentoJsonFormat() throws Exception {
        String json = "{\"valor\":250.00,\"data\":\"10/05/2026\",\"matriculaIds\":[1,2]}";
        CadastroPagamentoDTO dto = objectMapper.readValue(json, CadastroPagamentoDTO.class);

        assertEquals(LocalDate.of(2026, 5, 10), dto.getData());
        assertEquals(new BigDecimal("250.00"), dto.getValor());
    }

    @Test
    @DisplayName("EditarPagamentoDTO deve desserializar data no formato dd/MM/yyyy")
    void testEditarPagamentoJsonFormat() throws Exception {
        String json = "{\"valor\":300.00,\"data\":\"01/11/2026\"}";
        EditarPagamentoDTO dto = objectMapper.readValue(json, EditarPagamentoDTO.class);

        assertEquals(LocalDate.of(2026, 11, 1), dto.getData());
        assertEquals(new BigDecimal("300.00"), dto.getValor());
    }

    @Test
    @DisplayName("PagamentoDTO deve desserializar data no formato dd/MM/yyyy")
    void testPagamentoJsonFormat() throws Exception {
        String json = "{\"id\":10,\"valor\":180.00,\"data\":\"15/08/2026\"}";
        PagamentoDTO dto = objectMapper.readValue(json, PagamentoDTO.class);

        assertEquals(LocalDate.of(2026, 8, 15), dto.getData());
        assertEquals(10L, dto.getId());
        assertEquals(new BigDecimal("180.00"), dto.getValor());
    }
}
