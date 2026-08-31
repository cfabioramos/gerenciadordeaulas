package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    // Busca todas as aulas em uma data/hora específica
    List<Aula> findByData(LocalDateTime data);

    // Buscar por intervalo de data/hora:
    List<Aula> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    // Buscar todas as aulas de um ProgramaAula
    List<Aula> findByProgramaAulaId(Long programaAulaId);

    // Verifica se existem Aulas vinculadas ao ProgramaAula
    boolean existsByProgramaAulaId(Long programaAulaId);
}