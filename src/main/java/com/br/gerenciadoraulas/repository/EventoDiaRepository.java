package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.repository.EventoDiaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoDiaRepository extends JpaRepository<EventoDia, Long> {
    List<EventoDia> findByEventoId(Long eventoId);

    void deleteByEventoId(Long eventoId);
}