package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Ciclo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CicloRepositoryTest {

    @Autowired
    private CicloRepository cicloRepository;

    @Test
    void saveEFind() {
        Ciclo c = new Ciclo();
        c.setNome("Ciclo 1");
        c.setDataInicio(LocalDate.now());
        c.setDataFim(LocalDate.now().plusMonths(1));
        Ciclo saved = cicloRepository.save(c);
        assertTrue(cicloRepository.findById(saved.getId()).isPresent());
        assertEquals(1, cicloRepository.findAll().size());
    }
}
