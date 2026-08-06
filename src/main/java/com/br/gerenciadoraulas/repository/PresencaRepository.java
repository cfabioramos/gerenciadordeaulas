package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    // Remove presenças por matrícula e aula
    void deleteByMatriculaIdAndAulaId(Long matriculaId, Long aulaId);
}