package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.CicloDTO;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.service.CicloService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CicloController.class)
class CicloControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private CicloService cicloService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarTodas() throws Exception {
        when(cicloService.listarTodas()).thenReturn(List.of(new CicloDTO(new Ciclo())));
        mockMvc.perform(get("/ciclos").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdExistente() throws Exception {
        when(cicloService.buscarPorId(2L)).thenReturn(Optional.of(new CicloDTO(new Ciclo())));
        mockMvc.perform(get("/ciclos/2").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdInexistente() throws Exception {
        when(cicloService.buscarPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/ciclos/99").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar() throws Exception {
        when(cicloService.salvar(any(Ciclo.class))).thenReturn(new CicloDTO(new Ciclo()));
        mockMvc.perform(post("/ciclos").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Novo\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void atualizarExistente() throws Exception {
        when(cicloService.atualizar(any(Long.class), any(Ciclo.class))).thenReturn(Optional.of(new CicloDTO(new Ciclo())));
        mockMvc.perform(put("/ciclos/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Atualizado\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarInexistente() throws Exception {
        when(cicloService.atualizar(any(Long.class), any(Ciclo.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/ciclos/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"X\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/ciclos/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/ciclos")).andExpect(status().isUnauthorized());
    }
}
