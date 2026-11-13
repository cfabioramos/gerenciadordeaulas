package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.PagamentoDTO;
import com.br.gerenciadoraulas.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagamentoService pagamentoService;

    private String adminToken() {
        return JwtTokenProvider.generateToken("admin", true);
    }

    @Test
    void listarTodos() throws Exception {
        when(pagamentoService.listarTodos(null, null, null, null)).thenReturn(List.of());
        mockMvc.perform(get("/pagamentos").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void criar() throws Exception {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(1L);
        when(pagamentoService.salvar(any())).thenReturn(dto);
        mockMvc.perform(post("/pagamentos").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"valor\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listarPorAluno() throws Exception {
        when(pagamentoService.listarPorAluno(5L)).thenReturn(List.of());
        mockMvc.perform(get("/pagamentos/aluno/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarExistente() throws Exception {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(2L);
        when(pagamentoService.atualizar(any(Long.class), any())).thenReturn(Optional.of(dto));
        mockMvc.perform(put("/pagamentos/2").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"valor\":200}"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarInexistente() throws Exception {
        when(pagamentoService.atualizar(any(Long.class), any())).thenReturn(Optional.empty());
        mockMvc.perform(put("/pagamentos/2").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"valor\":200}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/pagamentos/3").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/pagamentos")).andExpect(status().isUnauthorized());
    }
}
