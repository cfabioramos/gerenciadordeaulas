package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.model.Usuario;
import com.br.gerenciadoraulas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    void loginSemCredenciaisRetorna400() throws Exception {
        mockMvc.perform(post("/auth/login").contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUsuarioInexistenteRetorna401() throws Exception {
        when(usuarioRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        mockMvc.perform(post("/auth/login").contentType("application/json")
                        .content("{\"usernameOrEmail\":\"u\",\"password\":\"p\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginSenhaIncorretaRetorna401() throws Exception {
        Usuario u = new Usuario("u", "e@e.com", org.mindrot.jbcrypt.BCrypt.hashpw("outra", org.mindrot.jbcrypt.BCrypt.gensalt()), true, "Nome");
        when(usuarioRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(u));
        mockMvc.perform(post("/auth/login").contentType("application/json")
                        .content("{\"usernameOrEmail\":\"u\",\"password\":\"p\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginSucessoRetornaToken() throws Exception {
        Usuario u = new Usuario("u", "e@e.com", org.mindrot.jbcrypt.BCrypt.hashpw("p", org.mindrot.jbcrypt.BCrypt.gensalt()), true, "Nome");
        when(usuarioRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(u));
        mockMvc.perform(post("/auth/login").contentType("application/json")
                        .content("{\"usernameOrEmail\":\"u\",\"password\":\"p\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("u"));
    }

    @Test
    void updateThemeSemTemaRetorna400() throws Exception {
        String token = JwtTokenProvider.generateToken("u", true);
        mockMvc.perform(put("/auth/theme").header("Authorization", "Bearer " + token)
                        .contentType("application/json").content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateThemeUsuarioInexistenteRetorna404() throws Exception {
        String token = JwtTokenProvider.generateToken("u", true);
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(put("/auth/theme").header("Authorization", "Bearer " + token)
                        .contentType("application/json").content("{\"theme\":\"forro\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateThemeSucesso() throws Exception {
        String token = JwtTokenProvider.generateToken("u", true);
        Usuario u = new Usuario("u", "e@e.com", "x", true, "Nome");
        when(usuarioRepository.findByUsername("u")).thenReturn(Optional.of(u));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(u);
        mockMvc.perform(put("/auth/theme").header("Authorization", "Bearer " + token)
                        .contentType("application/json").content("{\"theme\":\"forro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
