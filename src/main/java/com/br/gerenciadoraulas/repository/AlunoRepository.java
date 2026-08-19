package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Consulta alunos que NÃO estão matriculados em um ciclo específico
    @Query(
            """
                    SELECT a FROM Aluno a WHERE a.id NOT IN
                        (SELECT m.aluno.id FROM Matricula m WHERE m.programaAula.ciclo.id = :cicloId)"""
    )
    List<Aluno> findAlunosNaoMatriculados(@Param("cicloId") Long cicloId);
}