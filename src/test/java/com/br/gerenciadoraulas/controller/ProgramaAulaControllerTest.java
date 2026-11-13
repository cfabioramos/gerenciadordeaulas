package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.ProgramaAulaDTO;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.br.gerenciadoraulas.service.ProgramaAulaService;
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

@WebMvcTest(ProgramaAulaController.class)
class ProgramaAulaControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private ProgramaAulaService programaAulaService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarTodas() throws Exception {
        when(programaAulaService.listarTodas()).thenReturn(List.of());
        mockMvc.perform(get("/programa-aulas").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void listarPorCiclo() throws Exception {
        when(programaAulaService.listarPorCiclo(5L)).thenReturn(List.of());
        mockMvc.perform(get("/programa-aulas/ciclo/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdExistente() throws Exception {
        when(programaAulaService.buscarPorId(2L)).thenReturn(Optional.of(new ProgramaAulaDTO(new ProgramaAula())));
        mockMvc.perform(get("/programa-aulas/2").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdInexistente() throws Exception {
        when(programaAulaService.buscarPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/programa-aulas/99").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar() throws Exception {
        when(programaAulaService.salvar(any(ProgramaAula.class))).thenReturn(new ProgramaAulaDTO(new ProgramaAula()));
        mockMvc.perform(post("/programa-aulas").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"P\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void atualizarExistente() throws Exception {
        when(programaAulaService.atualizar(any(Long.class), any(ProgramaAula.class))).thenReturn(Optional.of(new ProgramaAulaDTO(new ProgramaAula())));
        mockMvc.perform(put("/programa-aulas/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"X\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizarInexistente() throws Exception {
        when(programaAulaService.atualizar(any(Long.class), any(ProgramaAula.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/programa-aulas/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"X\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/programa-aulas/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/programa-aulas")).andExpect(status().isUnauthorized());
    }
}
