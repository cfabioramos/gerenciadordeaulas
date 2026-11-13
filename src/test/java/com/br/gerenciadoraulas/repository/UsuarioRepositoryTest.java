package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void saveEFindByUsernameEEmailEUsernameOrEmail() {
        Usuario u = new Usuario("user1", "u@e.com", "hash", true, "Nome");
        usuarioRepository.save(u);

        assertTrue(usuarioRepository.findByUsername("user1").isPresent());
        assertTrue(usuarioRepository.findByEmail("u@e.com").isPresent());
        assertTrue(usuarioRepository.findByUsernameOrEmail("user1", "user1").isPresent());
        assertTrue(usuarioRepository.findByUsernameOrEmail("nope", "u@e.com").isPresent());
        assertTrue(usuarioRepository.findByUsername("nope").isEmpty());
    }
}
