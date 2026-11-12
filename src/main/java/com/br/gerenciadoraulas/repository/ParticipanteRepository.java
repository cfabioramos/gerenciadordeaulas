package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    List<Participante> findByEventoId(Long eventoId);
}