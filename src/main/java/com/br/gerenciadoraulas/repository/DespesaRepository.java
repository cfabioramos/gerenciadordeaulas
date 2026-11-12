package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByEventoId(Long eventoId);
}