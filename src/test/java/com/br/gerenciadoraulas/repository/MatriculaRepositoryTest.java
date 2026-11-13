package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class MatriculaRepositoryTest {

    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProgramaAulaRepository programaAulaRepository;
    @Autowired
    private CicloRepository cicloRepository;
    @Autowired
    private AulaRepository aulaRepository;

    @Test
    void consultarMatriculasPorAula() {
        Ciclo ciclo = new Ciclo();
        ciclo.setNome("C");
        ciclo.setDataInicio(LocalDate.now());
        ciclo.setDataFim(LocalDate.now().plusMonths(1));
        Ciclo savedCiclo = cicloRepository.save(ciclo);

        ProgramaAula pa = new ProgramaAula();
        pa.setNome("P");
        pa.setCiclo(savedCiclo);
        ProgramaAula savedPa = programaAulaRepository.save(pa);

        Aluno aluno = new Aluno();
        aluno.setNome("Ana");
        Aluno savedAluno = alunoRepository.save(aluno);

        Matricula m = new Matricula();
        m.setAluno(savedAluno);
        m.setProgramaAula(savedPa);
        m.setData(LocalDate.now());
        m.setFlAtivo(true);
        Matricula savedMat = matriculaRepository.save(m);

        Aula aula = new Aula();
        aula.setNome("Aula 1");
        aula.setData(LocalDateTime.now());
        aula.setProgramaAula(savedPa);
        Aula savedAula = aulaRepository.save(aula);

        List<MatriculaDTO> result = matriculaRepository.consultarMatriculasPorAula(savedAula.getId());
        assertEquals(1, result.size());
        assertEquals(savedMat.getId(), result.get(0).getId());
        assertEquals(savedAluno.getId(), result.get(0).getAlunoId());
        assertFalse(result.get(0).getPresente());
    }
}
