package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.dto.AlunoDTO;
import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AlunoService alunoService;

    private String adminToken() { return JwtTokenProvider.generateToken("admin", true); }

    @Test
    void listarTodos() throws Exception {
        when(alunoService.listarTodos()).thenReturn(List.of(new AlunoDTO(new Aluno())));
        mockMvc.perform(get("/alunos").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void listarNaoMatriculados() throws Exception {
        when(alunoService.listarNaoMatriculados(5L)).thenReturn(List.of());
        mockMvc.perform(get("/alunos/nao-matriculados/5").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void criar() throws Exception {
        when(alunoService.salvar(any(Aluno.class))).thenReturn(new AlunoDTO(new Aluno()));
        mockMvc.perform(post("/alunos").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Bia\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarPorId() throws Exception {
        when(alunoService.buscarPorId(3L)).thenReturn(new AlunoDTO(new Aluno()));
        mockMvc.perform(get("/alunos/3").header("Authorization", "Bearer " + adminToken()))
                .andExpect(status().isOk());
    }

    @Test
    void atualizar() throws Exception {
        when(alunoService.salvar(any(Aluno.class))).thenReturn(new AlunoDTO(new Aluno()));
        mockMvc.perform(put("/alunos/4").header("Authorization", "Bearer " + adminToken())
                        .contentType("application/json").content("{\"nome\":\"Atualizado\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void semTokenRetorna401() throws Exception {
        mockMvc.perform(get("/alunos")).andExpect(status().isUnauthorized());
    }
}
