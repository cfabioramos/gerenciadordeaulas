package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.AulaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.service.AulaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AulaController.class)
class AulaControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AulaService aulaService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarTodas() throws Exception {
        when(aulaService.listarTodas()).thenReturn(List.of(new AulaDTO(new Aula())));
        mockMvc.perform(get("/aulas").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdExistente() throws Exception {
        when(aulaService.buscarPorId(2L)).thenReturn(Optional.of(new AulaDTO(new Aula())));
        mockMvc.perform(get("/aulas/2").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdInexistente() throws Exception {
        when(aulaService.buscarPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/aulas/99").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar() throws Exception {
        when(aulaService.salvar(any(Aula.class))).thenReturn(new AulaDTO(new Aula()));
        mockMvc.perform(post("/aulas").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Aula\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarExistente() throws Exception {
        when(aulaService.atualizar(any(Long.class), any(Aula.class))).thenReturn(Optional.of(new AulaDTO(new Aula())));
        mockMvc.perform(put("/aulas/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"X\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarInexistente() throws Exception {
        when(aulaService.atualizar(any(Long.class), any(Aula.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/aulas/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"X\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/aulas/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorData() throws Exception {
        when(aulaService.findByData(any(LocalDateTime.class))).thenReturn(List.of());
        mockMvc.perform(get("/aulas/data/2026-01-01T10:00:00").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void listarPorProgramaAula() throws Exception {
        when(aulaService.listarPorProgramaAula(7L)).thenReturn(List.of());
        mockMvc.perform(get("/aulas/programaAula/7").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/aulas")).andExpect(status().isUnauthorized());
    }
}
