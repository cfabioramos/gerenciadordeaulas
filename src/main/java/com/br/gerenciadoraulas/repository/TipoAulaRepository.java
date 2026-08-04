package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.TipoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAulaRepository extends JpaRepository<TipoAula, Long> {}