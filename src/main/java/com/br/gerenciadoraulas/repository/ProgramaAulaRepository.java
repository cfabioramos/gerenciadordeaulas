package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.dto.AlunoDTO;
import com.br.gerenciadoraulas.model.ProgramaAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaAulaRepository extends JpaRepository<ProgramaAula, Long> {

    @Query(value = """
                SELECT a.id AS id,
                       a.nome AS nome,
                       CASE WHEN p.id IS NOT NULL THEN true ELSE false END AS presente
                FROM aluno a
                INNER JOIN matricula m ON m.aluno_id = a.id
                INNER JOIN programa_aula pa ON pa.id = m.programa_aula_id
                INNER JOIN aula au ON au.programa_aula_id = pa.id
                LEFT JOIN presenca p ON p.matricula_id = m.id AND p.aula_id = :aulaId
                WHERE au.id = :aulaId
            """, nativeQuery = true)
    List<AlunoDTO> consultarAlunosPorProgramaAula(
            @Param("programaAulaId") Long programaAulaId);

    // Retorna a lista de Programas de Aula pelo id do Ciclo associado
    List<ProgramaAula> findByCicloId(Long cicloId);
}