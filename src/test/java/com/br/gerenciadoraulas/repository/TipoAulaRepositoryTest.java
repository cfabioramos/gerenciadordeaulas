package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.TipoAula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TipoAulaRepositoryTest {

    @Autowired
    private TipoAulaRepository tipoAulaRepository;

    @Test
    void saveEFind() {
        TipoAula t = new TipoAula();
        t.setNome("Forró");
        TipoAula saved = tipoAulaRepository.save(t);
        assertTrue(tipoAulaRepository.findById(saved.getId()).isPresent());
        assertEquals(1, tipoAulaRepository.findAll().size());
    }
}
