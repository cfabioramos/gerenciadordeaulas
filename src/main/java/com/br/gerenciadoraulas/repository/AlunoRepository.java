package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.dto.AlunoDTO;
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

    // Pesquisa alunos (com flag de presença) para uma aula específica
    @Query(value = """
                SELECT a.id AS id,
                       a.nome AS nome,
                       CASE WHEN p.id IS NOT NULL THEN true ELSE false END AS presente
                FROM aluno a
                INNER JOIN matricula m ON m.aluno_id = a.id
                INNER JOIN programa_aula pa ON pa.id = m.programaaula_id
                INNER JOIN aula au ON au.programaaula_id = pa.id
                LEFT JOIN presenca p ON p.matricula_id = m.id AND p.aula_id = :aulaId
                WHERE au.id = :aulaId
            """, nativeQuery = true)
    List<AlunoDTO> consultarAlunosPorAula(@Param("aulaId") Long aulaId);
}