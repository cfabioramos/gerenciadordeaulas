package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByProgramaAulaId(Long programaAulaId);

    @Query("SELECT m FROM Matricula m WHERE m.aluno.id = :alunoId")
    List<Matricula> findByAlunoId(@Param("alunoId") Long alunoId);

    @Query("""
            SELECT new com.br.gerenciadoraulas.dto.MatriculaDTO(
                m.id,
                m.data,
                m.flAtivo,
                m.aluno.id,
                m.aluno.nome,
                m.programaAula.id,
                m.programaAula.nome,
                CASE WHEN p.id IS NOT NULL THEN true ELSE false END,
                p.id
            )
            FROM Matricula m
            JOIN m.programaAula pa
            JOIN Aula au ON au.programaAula.id = pa.id
            LEFT JOIN Presenca p ON p.matricula.id = m.id AND p.aula.id = :aulaId
            WHERE au.id = :aulaId
            """)
    List<MatriculaDTO> consultarMatriculasPorAula(@Param("aulaId") Long aulaId);

}