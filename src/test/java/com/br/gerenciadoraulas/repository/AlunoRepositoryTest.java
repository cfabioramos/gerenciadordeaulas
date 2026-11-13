package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CicloRepository cicloRepository;
    @Autowired
    private ProgramaAulaRepository programaAulaRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Test
    void findAlunosNaoMatriculados() {
        Ciclo ciclo = new Ciclo();
        ciclo.setNome("C");
        ciclo.setDataInicio(LocalDate.now());
        ciclo.setDataFim(LocalDate.now().plusMonths(1));
        Ciclo savedCiclo = cicloRepository.save(ciclo);

        ProgramaAula pa = new ProgramaAula();
        pa.setNome("P");
        pa.setCiclo(savedCiclo);
        ProgramaAula savedPa = programaAulaRepository.save(pa);

        Aluno a1 = new Aluno();
        a1.setNome("Matriculado");
        Aluno savedA1 = alunoRepository.save(a1);

        Aluno a2 = new Aluno();
        a2.setNome("NaoMatriculado");
        alunoRepository.save(a2);

        Matricula m = new Matricula();
        m.setAluno(savedA1);
        m.setProgramaAula(savedPa);
        m.setData(LocalDate.now());
        matriculaRepository.save(m);

        List<Aluno> naoMat = alunoRepository.findAlunosNaoMatriculados(savedCiclo.getId());
        assertEquals(1, naoMat.size());
        assertEquals("NaoMatriculado", naoMat.get(0).getNome());

        // ciclo inexistente -> todos os alunos (nenhum matriculado nesse ciclo)
        List<Aluno> todos = alunoRepository.findAlunosNaoMatriculados(999L);
        assertEquals(2, todos.size());
    }
}
