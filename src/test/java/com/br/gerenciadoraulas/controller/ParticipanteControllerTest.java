package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.service.ParticipanteService;
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

@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private ParticipanteService participanteService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarPorEvento() throws Exception {
        when(participanteService.listarPorEvento(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/participantes/evento/1").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void criar() throws Exception {
        Participante p = new Participante();
        p.setId(2L);
        when(participanteService.salvar(any(Participante.class))).thenReturn(p);
        mockMvc.perform(post("/api/participantes").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Ana\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/api/participantes/3").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/api/participantes/evento/1")).andExpect(status().isUnauthorized());
    }
}
