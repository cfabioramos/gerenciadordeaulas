package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Presenca;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PresencaRepositoryTest {

    @Autowired
    private PresencaRepository presencaRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Test
    void existsByAulaIdEDeleteByMatriculaIdAndAulaId() {
        Aula aula = new Aula();
        aula.setNome("A");
        aula = aulaRepository.save(aula);

        Matricula m = new Matricula();
        m = matriculaRepository.save(m);

        Presenca p = new Presenca();
        p.setAula(aula);
        p.setMatricula(m);
        presencaRepository.save(p);

        assertTrue(presencaRepository.existsByAulaId(aula.getId()));

        presencaRepository.deleteByMatriculaIdAndAulaId(m.getId(), aula.getId());
        assertFalse(presencaRepository.existsByAulaId(aula.getId()));
    }

    @Test
    void existsByAulaIdFalse() {
        assertFalse(presencaRepository.existsByAulaId(999L));
    }
}
