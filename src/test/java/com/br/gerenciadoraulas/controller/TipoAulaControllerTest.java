package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.TipoAulaDTO;
import com.br.gerenciadoraulas.repository.TipoAulaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoAulaController.class)
class TipoAulaControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private TipoAulaRepository tipoAulaRepository;

    @Test
    void listarTodos() throws Exception {
        String token = JwtTokenProvider.generateToken("admin", true);
        when(tipoAulaRepository.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/tipo-aulas").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
