package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.model.Despesa;
import com.br.gerenciadoraulas.service.DespesaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DespesaController.class)
class DespesaControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private DespesaService despesaService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarPorEvento() throws Exception {
        when(despesaService.listarPorEvento(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/despesas/evento/1").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void criar() throws Exception {
        Despesa d = new Despesa();
        d.setId(2L);
        when(despesaService.salvar(any(Despesa.class))).thenReturn(d);
        mockMvc.perform(post("/api/despesas").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"valor\":5}"))
                .andExpect(status().isOk());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/api/despesas/3").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/api/despesas/evento/1")).andExpect(status().isUnauthorized());
    }
}
