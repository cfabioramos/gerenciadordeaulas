package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findByTituloContainingIgnoreCase(String titulo);
    List<Evento> findByTipo(TipoEvento tipo);
}