package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProgramaAulaRepositoryTest {

    @Autowired
    private ProgramaAulaRepository programaAulaRepository;
    @Autowired
    private CicloRepository cicloRepository;

    @Test
    void findByCicloIdEExistsByCicloId() {
        Ciclo c = new Ciclo();
        c.setNome("C");
        c.setDataInicio(LocalDate.now());
        c.setDataFim(LocalDate.now().plusMonths(1));
        Ciclo savedCiclo = cicloRepository.save(c);

        ProgramaAula p = new ProgramaAula();
        p.setNome("P");
        p.setCiclo(savedCiclo);
        programaAulaRepository.save(p);

        assertEquals(1, programaAulaRepository.findByCicloId(savedCiclo.getId()).size());
        assertTrue(programaAulaRepository.existsByCicloId(savedCiclo.getId()));
        assertFalse(programaAulaRepository.existsByCicloId(999L));
        assertTrue(programaAulaRepository.findByCicloId(999L).isEmpty());
    }
}
