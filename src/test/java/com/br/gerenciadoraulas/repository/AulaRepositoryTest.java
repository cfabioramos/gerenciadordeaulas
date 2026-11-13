package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AulaRepositoryTest {

    @Autowired
    private AulaRepository aulaRepository;

    @Test
    void saveEFind() {
        Aula a = new Aula();
        a.setNome("Aula 1");
        a.setData(LocalDateTime.of(2026, 1, 5, 10, 0));
        Aula saved = aulaRepository.save(a);
        assertTrue(aulaRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void findByData() {
        LocalDateTime dt = LocalDateTime.of(2026, 2, 10, 19, 0);
        Aula a = new Aula();
        a.setNome("A");
        a.setData(dt);
        aulaRepository.save(a);

        List<Aula> result = aulaRepository.findByData(dt);
        assertEquals(1, result.size());
    }

    @Test
    void findByDataBetween() {
        LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 12, 31, 23, 59);
        Aula a = new Aula();
        a.setNome("A");
        a.setData(LocalDateTime.of(2026, 6, 15, 10, 0));
        aulaRepository.save(a);

        assertEquals(1, aulaRepository.findByDataBetween(inicio, fim).size());
        assertEquals(0, aulaRepository.findByDataBetween(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 12, 31, 23, 59)).size());
    }

    @Test
    void findByProgramaAulaIdInexistente() {
        assertTrue(aulaRepository.findByProgramaAulaId(999L).isEmpty());
        assertFalse(aulaRepository.existsByProgramaAulaId(999L));
    }
}
