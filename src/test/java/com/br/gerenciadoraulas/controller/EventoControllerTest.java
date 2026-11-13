package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.service.EventoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoController.class)
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoService eventoService;

    private String adminToken() {
        return JwtTokenProvider.generateToken("admin", true);
    }

    @Test
    void listarTodosRetorna200() throws Exception {
        Evento e = new Evento();
        e.setId(1L);
        e.setTitulo("Workshop");
        when(eventoService.listarTodos()).thenReturn(List.of(e));

        mockMvc.perform(get("/api/eventos").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Workshop"));
    }

    @Test
    void buscarPorIdExistenteRetorna200() throws Exception {
        Evento e = new Evento();
        e.setId(2L);
        e.setTitulo("Viagem");
        when(eventoService.buscarPorId(2L)).thenReturn(Optional.of(e));

        mockMvc.perform(get("/api/eventos/2").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Viagem"));
    }

    @Test
    void buscarPorIdInexistenteRetorna404() throws Exception {
        when(eventoService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/eventos/99").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvarRetorna200() throws Exception {
        Evento e = new Evento();
        e.setId(5L);
        e.setTitulo("Novo");
        when(eventoService.salvar(any(Evento.class))).thenReturn(e);

        mockMvc.perform(post("/api/eventos").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"titulo\":\"Novo\",\"tipo\":\"EVENTO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void atualizarExistenteRetorna200() throws Exception {
        Evento e = new Evento();
        e.setId(7L);
        e.setTitulo("Atualizado");
        when(eventoService.atualizar(eq(7L), any(Evento.class))).thenReturn(e);

        mockMvc.perform(put("/api/eventos/7").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"titulo\":\"Atualizado\",\"tipo\":\"EVENTO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizado"));
    }

    @Test
    void atualizarInexistenteRetorna404() throws Exception {
        when(eventoService.atualizar(eq(7L), any(Evento.class)))
                .thenThrow(new IllegalArgumentException("not found"));

        mockMvc.perform(put("/api/eventos/7").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json")
                        .content("{\"titulo\":\"X\",\"tipo\":\"EVENTO\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarRetorna204() throws Exception {
        mockMvc.perform(delete("/api/eventos/3").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/api/eventos"))
                .andExpect(status().isUnauthorized());
    }
}
