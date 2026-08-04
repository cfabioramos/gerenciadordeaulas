package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.ProgramaAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaAulaRepository extends JpaRepository<ProgramaAula, Long> {
}