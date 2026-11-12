package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByEventoId(Long eventoId);
}