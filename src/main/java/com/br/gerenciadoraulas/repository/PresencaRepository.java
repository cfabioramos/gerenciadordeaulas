package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    // Remove presenças por matrícula e aula (JPQL delete)
    @Modifying
    @Transactional
    @Query("delete from Presenca p where p.matricula.id = :matriculaId and p.aula.id = :aulaId")
    void deleteByMatriculaIdAndAulaId(@Param("matriculaId") Long matriculaId, @Param("aulaId") Long aulaId);
}